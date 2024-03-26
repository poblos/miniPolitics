package com.infernal_crew.mini_politics.game;


import com.infernal_crew.mini_politics.components.WarEvent;
import com.infernal_crew.mini_politics.indicators.Indicator;
import com.infernal_crew.mini_politics.indicators.IndicatorCondition;
import com.infernal_crew.mini_politics.indicators.IndicatorRelation;
import com.infernal_crew.mini_politics.modifiers.Modifier;
import com.infernal_crew.mini_politics.modifiers.ModifierCondition;
import com.infernal_crew.mini_politics.party.IdeologyCondition;
import com.infernal_crew.mini_politics.party.Party;
import com.infernal_crew.mini_politics.policy.Policy;
import com.infernal_crew.mini_politics.policy.PolicyCondition;
import com.infernal_crew.mini_politics.budget.*;
import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.event.Event;
import com.infernal_crew.mini_politics.event.Option;
import com.infernal_crew.mini_politics.jobs.*;
import com.infernal_crew.mini_politics.media.*;
import com.infernal_crew.mini_politics.story.StoryNote;
import com.infernal_crew.mini_politics.components.WarEvent;

import java.util.*;

public class Game {
    private final Map<Indicator, Float> values;
    private final Map<Job, Person> employed;

    private final Map<String, Modifier> activeModifiers;

    int round;

    public static final int ADVISOR_COOLDOWN = 20;
    private boolean displayNext;
    Event currentEvent;

    private Person currentPerson;
    private final Map<Integer, Policy> policies;
    private final List<Event> events;
    private final Map<Integer, Person> people;
    private final Map<Integer, Person> activePeople;
    private final Map<Integer, Integer> cooldown;
    private final List<Modifier> modifiers;
    private final List<MediaGroup> mediaGroups;
    private final List<StoryNote> storyNotes;
    private final List<WarEvent> warEvents = new ArrayList<>();
    private Party party;
    private final Budget budget;
    private final transient Random random;

    public Game(ArrayList<Event> events, ArrayList<Person> people, ArrayList<Person> activePeople, ArrayList<Policy> policies, ArrayList<Modifier> modifiers, ArrayList<MediaGroup> mediaGroups, Budget budget, List<StoryNote> storyNotes) {
        this.budget = budget;
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion, 40F);
        values.put(Indicator.StateStability, 35F);
        values.put(Indicator.PartySupport, 44F);
        values.put(Indicator.InfrastructureCorruption, 0F);
        values.put(Indicator.NarongWarBalance, 50F);
        this.round = 0;
        this.random = new Random();
        this.employed = new HashMap<>();
        this.policies = new HashMap<>();
        this.activeModifiers = new HashMap<>();
        this.cooldown = new HashMap<>();
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
        this.storyNotes = storyNotes;

    }

    private int eventProbabilitySum() {
        int acc = 0;
        for (Event event : events) {
            acc += event.getProbability(this);
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
                currentSum += e.getProbability(this);
                if (currentSum > draw) {
                    currentEvent = e;
                    break;
                }
            }
            while (!currentEvent.isEligible(this)) {
                draw = random.nextInt(probabilitySum);
                currentSum = 0;
                for (Event e : events) {
                    currentSum += e.getProbability(this);
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
    // Returns false if an additional menu should be displayed
    private boolean chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        boolean displayNext = true;
        for (Effect effect : effects) {
             displayNext = displayNext && effect.handle(this);
        }
        return displayNext;
    }

    public void takeOverMedia(Effect effect) {
        for (MediaGroup group : mediaGroups) {
            if (group.getId() == ((MediaTakeover) effect).getId()) {
                group.setAffiliation(((MediaTakeover) effect).getAffiliation());
            }
        }
    }

    public float includeBonus(float change, Indicator indicator) {
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
            } else if (employed.get(Job.Whip) != null && employed.get(Job.Whip).hasTrait(Trait.OldFart)) {
                bonus -= 0.2;
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
            if (getEmployed(job) != null) {
                getCooldown().put(getEmployed(job).getId(), ADVISOR_COOLDOWN);
                if (getEmployed(job).hasTrait(Trait.InfluentialInTheParty)) {
                    values.put(Indicator.PartyCohesion, values.get(Indicator.PartyCohesion) - 20);
                }
            }
            employed.put(job, currentPerson);
        } catch (Exception e) {
            employed.put(Job.values()[0], currentPerson);
        }
    }

    public void employ(Job job, int id) {
        try {
            if (getEmployed(job) != null) {
                getCooldown().put(getEmployed(job).getId(), ADVISOR_COOLDOWN);
                if (getEmployed(job).hasTrait(Trait.InfluentialInTheParty)) {
                    values.put(Indicator.PartyCohesion, values.get(Indicator.PartyCohesion) - 20);
                }
            }
            employed.put(job, activePeople.get(id));
        } catch (Exception e) {
            employed.put(Job.values()[0], activePeople.get(id));
        }
    }

    public void addModifier(String name) {
        for (Modifier modifier : modifiers) {
            if (Objects.equals(modifier.getName(), name)) {
                activeModifiers.put(name, modifier);
            }
        }
    }

    public void removeModifier(String name) {
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
        for(StoryNote note : storyNotes) {
            if (activeModifiers.containsKey(note.getModifier())) {
                note.setDone(true);
            }
        }
        displayNext = chooseOption(currentEvent, click);
        round++;
        for (Integer id : cooldown.keySet()) {
            if (cooldown.get(id) == 1) {
                cooldown.remove(id);
            } else {
                cooldown.put(id, cooldown.get(id) - 1);
            }
        }
        System.out.println("Events played: " + round);
    }

    public List<WarEvent> getWarEvents() {
        return warEvents;
    }

    public boolean isEmployed(int id) {
        for (Job job : employed.keySet()) {
            if (employed.get(job).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void updateIndicator(float change, Indicator indicator) {
        values.put(indicator, change + values.get(indicator));
        if (indicator == Indicator.NarongWarBalance) {
            warEvents.add(new WarEvent(currentEvent.getTitle(),change > 0 ? "+" + change : "-" + change));
        }
    }

    public Event getLoseEvent(Indicator indicator) {
        List<Option> list = new ArrayList<>();
        return new Event("You lost!","Your " + indicator + " was too low.", list,"darkside");
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

    public Random getRandom() { return random; }

    public Map<Integer, Policy> getPolicies() {
        return policies;
    }

    public Map<Job, Person> getAllEmployed() {
        return employed;
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

    public Map<Integer, Integer> getCooldown() {
        return cooldown;
    }

    public void setCurrentPerson(Person person) {
        currentPerson = person;
    }

    public List<StoryNote> getStoryNotes() {
        return storyNotes;
    }
}
