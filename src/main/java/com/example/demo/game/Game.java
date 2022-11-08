package com.example.demo.game;

import com.example.demo.budget.*;
import com.example.demo.event.*;
import com.example.demo.indicators.Indicator;
import com.example.demo.indicators.IndicatorChange;
import com.example.demo.indicators.IndicatorCondition;
import com.example.demo.indicators.IndicatorRelation;
import com.example.demo.jobs.*;
import com.example.demo.media.*;
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
    private final Map<Integer, Person> activePeople;
    private final List<Modifier> modifiers;
    private final List<MediaGroup> mediaGroups;
    private Party party;
    private final Budget budget;
    private final transient Random random;

    public Game(ArrayList<Event> events, ArrayList<Person> people, ArrayList<Person> activePeople, ArrayList<Policy> policies, ArrayList<Modifier> modifiers, ArrayList<MediaGroup> mediaGroups, Budget budget) {
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
        for (Person p : people) {
            this.people.put(p.getId(), p);
        }
        this.activePeople = new HashMap<>();
        for (Person p : activePeople) {
            this.activePeople.put(p.getId(), p);
            if (p.getStartingJob() != null) {
                employed.put(p.getStartingJob(), p);
            }
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

    public Map<Integer, Person> getPeople() {
        return people;
    }

    public Map<Integer, Person> getActivePeople() {
        return activePeople;
    }

    public float getIndicatorValue(Indicator name) {
        return values.get(name);
    }

    public Person getEmployed(Job job) {
        return employed.get(job);
    }

    public void setParty(Party party) {
        this.party = party;
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
        boolean certainFound = false;
        for (Event e : events) {
            if (e.isCertain() && e.isEligible(this)) {
                currentEvent = e;
                certainFound = true;
                break;
            }
        }

        if (!certainFound) {
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
                assert currentPerson != null;
                activePeople.put(currentPerson.getId(), currentPerson);
                return false;
            } else if (effect.getClass() == AdvisorEmployment.class) {
                currentPerson = people.get(((AdvisorEmployment) effect).getId());
                activePeople.put(currentPerson.getId(), currentPerson);
                return false;
            } else if (effect.getClass() == RandomAdvisorDismissal.class) {
                Job job = employed.keySet().stream().skip(random.nextInt(employed.size())).findFirst().orElse(null);
                employed.remove(job);
            } else if (effect.getClass() == AdvisorDismissal.class) {
                employed.remove(((AdvisorDismissal) effect).getJob());
            } else if (effect.getClass() == ModifierInvocation.class) {
                addModifier(((ModifierInvocation) effect).getName());
            } else if (effect.getClass() == ModifierRemoval.class) {
                removeModifier(((ModifierRemoval) effect).getName());
            } else if (effect.getClass() == MediaTakeover.class) {
                takeOverMedia(effect);
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

    private void takeOverMedia(Effect effect) {
        for (MediaGroup group : mediaGroups) {
            if (group.getId() == ((MediaTakeover) effect).getId()) {
                group.setAffiliation(((MediaTakeover) effect).getAffiliation());
            }
        }
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

    public void employ(Job job) {
        try {
            employed.put(job, currentPerson);
        } catch (Exception e) {
            employed.put(Job.values()[0], currentPerson);
        }
    }

    public void employ(Job job, int id) {
        try {
            employed.put(job, activePeople.get(id));
        } catch (Exception e) {
            employed.put(Job.values()[0], activePeople.get(id));
        }
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
        } else if (condition.getClass() == MediaIdCondition.class) {
            for (MediaGroup group : mediaGroups) {
                if (group.getId() == ((MediaIdCondition) condition).getId()) {
                    return group.getAffiliation() == ((MediaIdCondition) condition).getAffiliation();
                }
            }
            return hasAffiliated(((MediaIdCondition) condition).getAffiliation());
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

    public void handleEvent(int click) {
        displayNext = chooseOption(currentEvent, click);
        round++;
        System.out.println("Events played: " + round);
    }
}
