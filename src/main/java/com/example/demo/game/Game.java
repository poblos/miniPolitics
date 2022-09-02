package com.example.demo.game;

import com.example.demo.budget.*;
import com.example.demo.event.*;
import com.example.demo.indicators.Indicator;
import com.example.demo.indicators.IndicatorChange;
import com.example.demo.indicators.IndicatorCondition;
import com.example.demo.indicators.IndicatorRelation;
import com.example.demo.jobs.*;
import com.example.demo.media.Affiliation;
import com.example.demo.media.MediaCondition;
import com.example.demo.media.MediaGroup;
import com.example.demo.media.MediaTakeover;
import com.example.demo.modifiers.Modifier;
import com.example.demo.modifiers.ModifierCondition;
import com.example.demo.modifiers.ModifierInvocation;
import com.example.demo.modifiers.ModifierRemoval;
import com.example.demo.party.IdeologyChange;
import com.example.demo.party.IdeologyCondition;
import com.example.demo.party.Party;
import com.example.demo.policy.Policy;
import com.example.demo.policy.PolicyChange;
import com.example.demo.policy.PolicyCondition;
import javafx.collections.ObservableList;

import java.util.*;

public class Game {
    private final Map<Indicator, Float> values;
    private final Map<Job, Person> employed;
    private final Map<String, Modifier> activeModifiers;

    int round;
    private boolean displayNext;
    Event currentEvent;

    private Person currentPerson;
    private final Map<Integer, Policy> policies;
    private final List<Event> events;
    private final Map<Integer, Person> people;
    private final List<Modifier> modifiers;
    private final List<MediaGroup> mediaGroups;
    private final Party party;
    private final Budget budget;
    private final transient Random random;

    public Game(ArrayList<Event> events, ArrayList<Person> people, ArrayList<Policy> policies, ArrayList<Modifier> modifiers, ArrayList<MediaGroup> mediaGroups, Party party, Budget budget) {
        this.budget = budget;
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion, 40F);
        values.put(Indicator.StateStability, 35F);
        values.put(Indicator.PartySupport, 44F);
        this.round = 0;
        this.random = new Random();
        this.employed = new HashMap<>();
        this.policies = new HashMap<>();
        this.activeModifiers = new HashMap<>();
        this.events = events;
        this.people = new HashMap<>();
        this.party = party;
        for (Person p : people) {
            this.people.put(p.getId(), p);
        }
        for (Policy p : policies) {
            this.policies.put(p.getId(), p);
        }
        this.modifiers = modifiers;
        this.mediaGroups = mediaGroups;

    }

    public Event getCurrentEvent() {
        return currentEvent;
    }
    public boolean displayNext() {
        return displayNext;
    }
    public Person getCurrentPerson() {
        return currentPerson;
    }
    public List<MediaGroup> getMediaGroups() {
        return mediaGroups;
    }

    public Party getParty() {
        return party;
    }

    public Budget getBudget() {
        return budget;
    }

    public int getRound() {
        return round;
    }

    public Map<Integer, Policy> getPolicies() {
        return policies;
    }

    private int eventProbability(Event event) {
        if (event.getProbability() == 0) {
            event.setProbability(50);
            return (50);
        } else if (event.getProbabilityChangers() == null) {
            return (event.getProbability());
        } else {
            int acc = event.getProbability();
            for (ProbabilityChanger changer : event.getProbabilityChangers()) {
                boolean isAdded = true;
                for (Condition condition : changer.conditions()) {
                    if (!meetsCondition(condition)) {
                        isAdded = false;
                        break;
                    }
                }
                if (isAdded) {
                    acc += changer.probChange();
                }
            }
            return acc;
        }
    }

    private int eventProbabilitySum() {
        int acc = 0;
        for (Event event : events) {
            acc += eventProbability(event);
        }
        return acc;
    }

    public void chooseEvent() {
        int probabilitySum = eventProbabilitySum();
        int draw = random.nextInt(probabilitySum);
        int currentSum = 0;
        for (Event e : events) {
            currentSum += e.getProbability();
            if (currentSum > draw) {
                currentEvent = e;
                break;
            }
        }
        while (!currentEvent.isEligible(this)) {
            draw = random.nextInt(probabilitySum);
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
        currentEvent = currentEvent.adjust(this);
    }

    // Returns true if the next Event should be displayed
    private boolean chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        for (Effect effect : effects) {
            if (effect.getClass() == IndicatorChange.class) {
                float change = ((IndicatorChange) effect).change();
                Indicator indicator = ((IndicatorChange) effect).indicator();
                change = includeBonus(change, indicator);
                values.put(indicator, change + values.get(indicator));
            } else if (effect.getClass() == RandomAdvisorEmployment.class) {

                currentPerson = people.values().stream().skip(random.nextInt(people.size())).findFirst().orElse(null);
                return false;
            } else if (effect.getClass() == AdvisorEmployment.class) {
                Person newPerson = people.get(((AdvisorEmployment) effect).getId());
                currentPerson = newPerson;
                return false;
            } else if (effect.getClass() == RandomAdvisorDismissal.class) {
                Job job = employed.keySet().stream().skip(random.nextInt(employed.size())).findFirst().orElse(null);
                fireFromJob(job);
            } else if (effect.getClass() == AdvisorDismissal.class) {
                fireFromJob(((AdvisorDismissal) effect).getJob());
            } else if (effect.getClass() == ModifierInvocation.class) {
                addModifier(((ModifierInvocation) effect).getName());
            } else if (effect.getClass() == ModifierRemoval.class) {
                removeModifier(((ModifierRemoval) effect).getName());
            } else if (effect.getClass() == MediaTakeover.class) {
                takeOverMedia();
            } else if (effect.getClass() == IdeologyChange.class) {
                party.ideologies().remove(((IdeologyChange) effect).getRemoved());
                party.ideologies().add(((IdeologyChange) effect).getAdded());
            } else if (effect.getClass() == PolicyChange.class) {
                policies.get(((PolicyChange) effect).id()).setCurrentOption(((PolicyChange) effect).option());
            } else if (effect.getClass() == BudgetIncome.class) {
                IncomeCategory category = ((BudgetIncome) effect).category();
                budget.getIncome().put(category, budget.getIncome().get(category) + ((BudgetIncome) effect).change());
            } else if (effect.getClass() == BudgetExpense.class) {
                ExpenseCategory category = ((BudgetExpense) effect).category();
                budget.getExpenses().put(category, budget.getExpenses().get(category) + ((BudgetExpense) effect).change());
            }

        }
        return true;
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

    public void chooseJob(Job job) {
        try {
            employed.put(job, currentPerson);
        } catch (Exception e) {
            employed.put(Job.values()[0], currentPerson);
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
        } else if (condition.getClass() == IdeologyCondition.class) {
            return party.ideologies().contains(((IdeologyCondition) condition).getIdeology());
        } else if (condition.getClass() == PolicyCondition.class) {
            return policies.get(((PolicyCondition) condition).id()).getCurrentOption() == ((PolicyCondition) condition).option();
        } else if (condition.getClass() == RoundCondition.class) {
            return round >= ((RoundCondition) condition).round();
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

    public Person getEmployed(Job job) {
        return employed.get(job);
    }


    public void handleEvent(int click) {
        displayNext = chooseOption(currentEvent, click);
        round++;
        System.out.println("Events played: " + round);
    }
}
