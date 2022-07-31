package game;

import effects.*;
import events_classes.*;
import indicators.Indicator;
import jobs.*;
import media_classes.Affiliation;
import media_classes.MediaGroup;
import gui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Game implements ActionListener {
    private final Map<Indicator, Float> values;
    private final Map<Job, Person> employed;
    private final Map<String, Modifier> activeModifiers;

    int round;
    Event currentEvent;
    int eventProbabilitySum;
    List<Event> events;
    Map<Integer, Person> people;
    List<Modifier> modifiers;
    List<MediaGroup> mediaGroups;
    private final transient Random random;
    private final transient Scanner scanner;

    GUI gui;

    public Game(ArrayList<Event> events, ArrayList<Person> people, ArrayList<Modifier> modifiers, ArrayList<MediaGroup> mediaGroups) {
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion, 40F);
        values.put(Indicator.StateStability, 35F);
        values.put(Indicator.PartySupport, 44F);
        this.round = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.employed = new HashMap<>();
        this.activeModifiers = new HashMap<>();
        this.events = events;
        this.people = new HashMap<>();
        for (Person p : people) {
            this.people.put(p.getId(), p);
        }
        this.modifiers = modifiers;
        this.mediaGroups = mediaGroups;
        for (Event e : events) {
            if (e.getProbability() == 0) {
                e.setProbability(50);
            }
            this.eventProbabilitySum += e.getProbability();
        }

    }

    public void textSimulate(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("\nCurrent stats:\n" + values.toString());
            System.out.println(employed.toString());
            System.out.println(activeModifiers.toString());
            System.out.println(mediaGroups.toString() + "\n");

            Event currentEvent = events.get(random.nextInt(events.size()));
            currentEvent = currentEvent.adjust(this);
            System.out.println(currentEvent);

            int option = scanner.nextInt();
            try {
                chooseOption(currentEvent, option);
            } catch (Exception e) {
                chooseOption(currentEvent, 0);
            }
            round++;
        }
    }

    public void windowSimulate(int numberOfRounds) {
        gui = new GUI(this);
        gui.updateStats();
        currentEvent = chooseEvent();
        gui.newEvent(currentEvent, this);
    }

    private Event chooseEvent() {
        int draw = random.nextInt(eventProbabilitySum);
        int currentSum = 0;
        for (Event e : events) {
            currentSum += e.getProbability();
            if (currentSum > draw) {
                currentEvent = e;
                break;
            }
        }
        while (!currentEvent.isEligible(this)) {
            draw = random.nextInt(eventProbabilitySum);
            currentSum = 0;
            for (Event e : events) {
                currentSum += e.getProbability();
                if (currentSum > draw) {
                    currentEvent = e;
                    break;
                }
            }
        }
        if (currentEvent.isUnique()) {
            events.remove(currentEvent);
        }
        return (currentEvent.adjust(this));
    }

    // Returns true if the additional gui menu needs to be displayed
    private boolean chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        for (Effect effect : effects) {
            if (effect.getClass() == IndicatorChange.class) {
                float change = ((IndicatorChange) effect).getChange();
                Indicator indicator = ((IndicatorChange) effect).getIndicator();
                change = includeBonus(change, indicator);
                values.put(indicator, change + values.get(indicator));
            } else if (effect.getClass() == RandomAdvisorEmployment.class) {
                Person newPerson = people.values().stream()
                        .skip(random.nextInt(people.size()))
                        .findFirst().get();
                if (gui == null) {
                    chooseJob(newPerson);
                } else {
                    gui.jobWindow(newPerson);
                    return true;    
                }
            } else if (effect.getClass() == AdvisorEmployment.class) {
                Person newPerson = people.get(((AdvisorEmployment) effect).getId());
                if (gui == null) {
                    chooseJob(newPerson);
                } else {
                    gui.jobWindow(newPerson);
                    return true;
                }
            } else if (effect.getClass() == RandomAdvisorDismissal.class) {
                Job job = employed.keySet().stream()
                        .skip(random.nextInt(employed.size()))
                        .findFirst().get();
                fireFromJob(job);
            } else if (effect.getClass() == AdvisorDismissal.class) {
                fireFromJob(((AdvisorDismissal) effect).getJob());
            } else if (effect.getClass() == ModifierInvocation.class) {
                addModifier(((ModifierInvocation) effect).getName());
            } else if (effect.getClass() == ModifierRemoval.class) {
                removeModifier(((ModifierRemoval) effect).getName());
            } else if (effect.getClass() == MediaTakeover.class) {
                takeOverMedia();
            }

        }
        return false;
    }

    private void takeOverMedia() {
        MediaGroup mediaGroup = mediaGroups.get(random.nextInt(mediaGroups.size()));
        int i = 0;
        while (mediaGroup.getAffiliation() == Affiliation.Government && i < 10) {
            mediaGroup = mediaGroups.get(random.nextInt(mediaGroups.size()));
            i++;
        }
        mediaGroup.setAffiliation(Affiliation.Government);
    }

    private float includeBonus(float change, Indicator indicator) {
        if (indicator == Indicator.PartySupport) {
            float bonus = 1;
            if (employed.get(Job.Propagandist) != null && employed.get(Job.Propagandist).hasTrait(Trait.PropagandaMaster)) {
                bonus += 0.2;
            }
            if (change > 0) {
                change *= bonus;
            } else {
                change /= bonus;
            }
        } else if (indicator == Indicator.PartyCohesion) {
            float bonus = 1;
            if (employed.get(Job.Whip) != null && employed.get(Job.Whip).hasTrait(Trait.IronFist)) {
                bonus += 0.2;
            }
            if (change > 0) {
                change *= bonus;
            } else {
                change /= bonus;
            }
        } else if (indicator == Indicator.StateStability) {
            float bonus = 1;
            if (employed.get(Job.Strategist) != null && employed.get(Job.Strategist).hasTrait(Trait.Statesman)) {
                bonus += 0.2;
            }
            if (change > 0) {
                change *= bonus;
            } else {
                change /= bonus;
            }
        }
        return change;
    }

    private void chooseJob(Person newPerson) {
        System.out.println("Choose job for:" + newPerson);
        System.out.println(Arrays.toString(Job.values()));
        int option = scanner.nextInt();
        try {
            employed.put(Job.values()[option], newPerson);
        } catch (Exception e) {
            employed.put(Job.values()[0], newPerson);
        }
    }

    private void fireFromJob(Job job) {
        employed.remove(job);
    }

    private void addModifier(String name) {
        for (Modifier modifier : modifiers) {
            if (Objects.equals(modifier.getName(), name)) {
                activeModifiers.put(name, modifier);
            }
        }
    }

    private void removeModifier(String name) {
        for (Modifier modifier : modifiers) {
            if (Objects.equals(modifier.getName(), name)) {
                activeModifiers.remove(name);
            }
        }
    }

    public boolean meetsConditions(Option option) {
        if (option.getTrigger() == null) {
            return true;
        }
        for (Condition condition : option.getTrigger().getYes()) {
            if (!meetsCondition(condition)) {
                return false;
            }
        }
        for (Condition condition : option.getTrigger().getNo()) {
            if (meetsCondition(condition)) {
                return false;
            }
        }
        return true;
    }

    public boolean meetsCondition(Condition condition) {
        if (condition.getClass() == ModifierCondition.class) {
            return activeModifiers.containsKey(((ModifierCondition) condition).getName());
        } else if (condition.getClass() == AdvisorCondition.class) {
            return employed.containsKey(((AdvisorCondition) condition).getJob());
        } else if (condition.getClass() == SomeAdvisorCondition.class) {
            return !employed.isEmpty();
        } else if (condition.getClass() == MediaCondition.class) {
            return hasAffiliated(((MediaCondition) condition).getAffiliation());
        } else if (condition.getClass() == AdvisorSkillCondition.class) {
            Job job = ((AdvisorSkillCondition) condition).job();
            Trait trait = ((AdvisorSkillCondition) condition).trait();
            return employed.containsKey(job) && employed.get(job).getTraits().contains(trait);
        } else if (condition.getClass() == IndicatorCondition.class) {
            Indicator indicator = ((IndicatorCondition) condition).getIndicator();
            IndicatorRelation relation = ((IndicatorCondition) condition).getRelation();
            int value = ((IndicatorCondition) condition).getValue();
            if (relation == IndicatorRelation.Higher) {
                return this.getIndicatorValue(indicator) > value;
            } else {
                return this.getIndicatorValue(indicator) < value;
            }
        }
        return false;
    }

    private boolean hasAffiliated(Affiliation affiliation) {
        for (MediaGroup mg : mediaGroups) {
            if (mg.getAffiliation() == affiliation) {
                return true;
            }
        }
        return false;
    }

    public float getIndicatorValue(Indicator name) {
        return values.get(name);
    }

    public Person getEmployed(Job job) {return employed.get(job);}

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean displayNext;
        if (e.getSource().getClass() == EventButton.class) {
            displayNext = chooseOption(currentEvent, ((EventButton) e.getSource()).getId());
            if (displayNext) {
                return;
            }
        } else if (e.getSource().getClass() == JobButton.class) {
            employed.put(Job.values()[((JobButton) e.getSource()).getId()], ((JobButton) e.getSource()).getPerson());
        }
        round++;
        gui.updateStats();
        currentEvent = chooseEvent();
        gui.newEvent(currentEvent, this);

    }
}
