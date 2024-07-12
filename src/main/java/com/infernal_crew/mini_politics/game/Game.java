package com.infernal_crew.mini_politics.game;

import com.infernal_crew.mini_politics.components.WarEvent;
import com.infernal_crew.mini_politics.event.*;
import com.infernal_crew.mini_politics.indicators.Indicator;
import com.infernal_crew.mini_politics.modifiers.Modifier;
import com.infernal_crew.mini_politics.party.Faction;
import com.infernal_crew.mini_politics.party.Party;
import com.infernal_crew.mini_politics.policy.Policy;
import com.infernal_crew.mini_politics.budget.*;
import com.infernal_crew.mini_politics.jobs.*;
import com.infernal_crew.mini_politics.media.*;
import com.infernal_crew.mini_politics.story.StoryNote;

import java.util.*;

import static com.infernal_crew.mini_politics.utils.DataManipulator.toIdMap;

public class Game {
    private final Map<Indicator, Float> values = new HashMap<>();
    private final Map<Job, Person> employed = new HashMap<>();
    private final Map<String, Modifier> activeModifiers = new HashMap<>();

    int round = 0;

    public static final int ADVISOR_COOLDOWN = 20;

    private String dialogueId = null;
    Event currentEvent;

    private Person currentPerson;
    private final Map<Integer, Policy> policies = new HashMap<>();
    private final List<Event> events;
    private final Map<String, Dialogue> dialogues;
    private final Map<Integer, Person> people = new HashMap<>();
    private final Map<String, Trait> traits;
    private final Map<Integer, Person> activePeople = new HashMap<>();
    private final Map<Integer, Integer> cooldown = new HashMap<>();
    private final List<Modifier> modifiers;
    private final List<MediaGroup> mediaGroups;
    private final Map<String, Party> parties;
    private final Map<String, Faction> factions;
    private final Map<String, StoryNote> storyNotes;
    private final List<WarEvent> warEvents = new ArrayList<>();
    private Party rulingParty;
    private final Budget budget;
    private final transient Random random = new Random();

    public Game(List<Event> events, List<Dialogue> dialogues, List<Person> people, List<Person> activePeople, List<Policy> policies,
                List<Modifier> modifiers, List<MediaGroup> mediaGroups, Budget budget, List<StoryNote> storyNotes, List<Trait> traits, List<Party> parties, List<Faction> factions) {
        this.budget = budget;
        this.events = events;
        this.modifiers = modifiers;
        this.mediaGroups = mediaGroups;

        this.storyNotes = toIdMap(storyNotes);

        this.parties = toIdMap(parties);
        this.factions = toIdMap(factions);
        this.dialogues = toIdMap(dialogues);

        for (Person p : people) {
            this.people.put(p.getId(), p);
        }
        for (Person p : activePeople) {
            this.activePeople.put(p.getId(), p);
            if (p.getStartingJob() != null) {
                employed.put(p.getStartingJob(), p);
            }
        }
        for (Policy p : policies) {
            this.policies.put(p.getId(), p);
        }

        this.traits = toIdMap(traits);

        for (Party p : parties) {
            if (Objects.equals(p.getId(), "01")) {
                rulingParty = p;
            }
        }

        values.put(Indicator.PartyCohesion, calculatePartyCohesion());
        values.put(Indicator.StateStability, 35F);
        values.put(Indicator.PartySupport, 44F);
        values.put(Indicator.InfrastructureCorruption, 0F);
        values.put(Indicator.NarongWarBalance, 50F);
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

    private void chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        for (Effect effect : effects) {
            effect.handle(this);
        }
    }

    public void handleOption(Option option) {
        List<Effect> effects = option.getEffects();
        for (Effect effect : effects) {
            effect.handle(this);
        }
    }

    public float includeBonus(float change, Indicator indicator) {
        float bonus = 1;
        for (Job job : employed.keySet()) {
            for (String trait : employed.get(job).getTraits()) {
                if (traits.get(trait) != null) {
                    for (TraitEffect effect : traits.get(trait).getEffects())
                        bonus += effect.calculateBonus(job, indicator) / 100.0f;
                }
            }
        }
        if (change > 0) {
            change *= bonus;
        } else {
            change /= bonus;
        }

        return change;
    }

    public void employ(Job job) {
        try {
            if (getEmployed(job) != null) {
                dismiss(job);
            }
            employed.put(job, currentPerson);
        } catch (Exception e) {
            employed.put(Job.values()[0], currentPerson);
        }
    }

    public void dismiss(Job job) {
        getCooldown().put(getEmployed(job).getId(), ADVISOR_COOLDOWN);
        for (String trait : employed.get(job).getTraits()) {
            if (traits.get(trait) != null) {
                for (TraitEffect effect : traits.get(trait).getEffects())
                    effect.handle(this);
            }
        }
    }

    public void employ(Job job, int id) {
        try {
            if (getEmployed(job) != null) {
                dismiss(job);
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

    public boolean hasAffiliated(Affiliation affiliation) {
        for (MediaGroup mg : mediaGroups) {
            if (mg.getAffiliation() == affiliation) {
                return true;
            }
        }
        return false;
    }

    public void handleEvent(int click) {
        for (StoryNote note : storyNotes.values()) {
            if (activeModifiers.containsKey(note.getModifier())) {
                note.setDone(true);
            }
        }
        chooseOption(currentEvent, click);
        round++;
        for (Integer id : cooldown.keySet()) {
            if (cooldown.get(id) == 1) {
                cooldown.remove(id);
            } else {
                cooldown.put(id, cooldown.get(id) - 1);
            }
        }
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
        if (indicator == Indicator.PartyCohesion) {
            for(String id : factions.keySet()) {
                updateLoyalty(id, change);
            }
        } else {
            values.put(indicator, change + values.get(indicator));
            if (indicator == Indicator.NarongWarBalance) {
                warEvents.add(new WarEvent(currentEvent.getTitle(), change > 0 ? "+" + change : Float.toString(change)));
            }
        }

    }

    private float calculatePartyCohesion() {
        double sum = 0.0;
        double totalMembers = 0.0;
        for(Faction f : factions.values()) {
            sum += f.getLoyalty() * f.getMembers();
            totalMembers += f.getMembers();
        }
        return (float) (sum/ totalMembers);
    }

    public Event getLoseEvent(Indicator indicator) {
        List<Option> list = new ArrayList<>();
        return new Event("You lost!", "Your " + indicator + " was too low.", list, "darkside");
    }

    public void updateLoyalty(String id, float finalChange) {
        Faction f = factions.get(id);
        f.setLoyalty(f.getLoyalty() + finalChange);
        values.put(Indicator.PartyCohesion, calculatePartyCohesion());
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public List<MediaGroup> getMediaGroups() {
        return mediaGroups;
    }

    public Map<String, Party> getParties() {
        return parties;
    }

    public Party getRulingParty() {
        return rulingParty;
    }

    public Budget getBudget() {
        return budget;
    }

    public int getRound() {
        return round;
    }

    public Random getRandom() {
        return random;
    }

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

    public Map<Job, Person> getEmployed() {
        return employed;
    }

    public void setRulingParty(Party rulingParty) {
        this.rulingParty = rulingParty;
    }

    public Map<Integer, Integer> getCooldown() {
        return cooldown;
    }

    public void setCurrentPerson(Person person) {
        currentPerson = person;
    }

    public Map<String,StoryNote> getStoryNotes() {
        return storyNotes;
    }

    public Map<String, Modifier> getActiveModifiers() {
        return activeModifiers;
    }

    public void setDialogueId(String dialogueId) {
        this.dialogueId = dialogueId;
    }

    public String getDialogueId() {
        return dialogueId;
    }

    public Dialogue getDialogue(String id) {
        return dialogues.get(id);
    }

    public Map<String, Faction> getFactions() {
        return factions;
    }
}
