package game;

import effects.*;
import events_classes.*;
import indicators.Indicator;
import jobs.*;
import media_classes.Affiliation;
import media_classes.MediaGroup;
import gui.*;

import java.util.*;

public class Game {
    Map<Indicator, Float> values;
    Map<Job, Person> employed;
    Map<String, Modifier> activeModifiers;

    int round;
    List<Event> events;
    List<Person> people;
    List<Modifier> modifiers;
    List<MediaGroup> mediaGroups;
    private final transient Random random;
    private final transient Scanner scanner;

    GUI gui;
    EventDisplay okno;

    public Game(ArrayList<Event> events, ArrayList<Person> people, ArrayList<Modifier> modifiers, ArrayList<MediaGroup> mediaGroups) {
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion, 75F);
        values.put(Indicator.StateStability, 75F);
        values.put(Indicator.PartySupport, 75F);
        this.round = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.employed = new HashMap<>();
        this.activeModifiers = new HashMap<>();
        this.events = events;
        this.people = people;
        this.modifiers = modifiers;
        this.mediaGroups = mediaGroups;
        gui = new GUI(this);
    }

    public void symuluj(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("\nCurrent stats:\n" + values.toString());
            System.out.println(employed.toString());
            System.out.println(activeModifiers.toString());
            System.out.println(mediaGroups.toString()+ "\n");
            Event currentEvent = events.get(random.nextInt(events.size()));
            currentEvent = currentEvent.adjust(this);
            okno = new EventDisplay(currentEvent, gui);
            System.out.println(currentEvent);
            int option = scanner.nextInt();
            try {
                chooseOption(currentEvent, option);
            } catch (Exception e) {
                chooseOption(currentEvent, 0);
            }
            round++;
            gui.updateStats();
        }
    }

    private void chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        for (Effect effect : effects) {
            if (effect.getClass() == IndicatorChange.class) {
                float change = ((IndicatorChange) effect).getChange();
                Indicator indicator = ((IndicatorChange) effect).getIndicator();
                change = includeBonus(change, indicator);
                values.put(indicator, change + values.get(indicator));
            } else if (effect.getClass() == AdvisorEmployment.class) {
                Person newPerson = people.get(random.nextInt(people.size()));
                chooseJob(newPerson);
            } else if (effect.getClass() == AdvisorDismissal.class) {
                fireFromJob(((AdvisorDismissal) effect).getJob());
            } else if (effect.getClass() == ModifierInvocation.class) {
                addModifier(((ModifierInvocation) effect).getName());
            } else if (effect.getClass() == MediaTakeover.class) {
                takeOverMedia();
            }

        }
    }

    private void takeOverMedia() {
        MediaGroup mediaGroup = mediaGroups.get(random.nextInt(mediaGroups.size()));
        int i=0;
        while (mediaGroup.getAffiliation() == Affiliation.Government && i<10) {
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

    private boolean meetsCondition(Condition condition) {
        if (condition.getClass() == ModifierCondition.class) {
            return activeModifiers.containsKey(((ModifierCondition) condition).getName());
        } else if (condition.getClass() == AdvisorCondition.class) {
            return employed.containsKey(((AdvisorCondition) condition).getJob());
        } else if (condition.getClass() == MediaCondition.class) {
            return hasAffilliated(((MediaCondition) condition).getAffiliation());
        }
        return false;
    }

    private boolean hasAffilliated(Affiliation affiliation) {
        for(MediaGroup mg : mediaGroups) {
            if (mg.getAffiliation() == affiliation) {
                return true;
            }
        }
        return false;
    }

    public Float getIndicatorValue(Indicator name) {
        return values.get(name);
    }
}
