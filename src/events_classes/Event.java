package events_classes;

import game.Game;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String title;
    private final String description;
    private final List<Option> options;
    private final Trigger trigger;
    private int probability;

    public Event(String title, String description, List<Option> options) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = new Trigger();
    }

    public Event(String title, String description, List<Option> options, Trigger trigger) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = trigger;
    }


    public Event(String title, String description, List<Option> options, Trigger trigger, int probability) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = trigger;
        this.probability = probability;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public Event adjust(Game game) {
        ArrayList<Option> newOptions = new ArrayList<>();
        for (Option option : options) {
            if (game.meetsConditions(option)) {
                newOptions.add(option);
            }
        }
        return new Event(title, description, newOptions);
    }

    public boolean isEligible(Game game) {
        if (trigger == null) {
            return true;
        }
        for (Condition condition : trigger.getYes()) {
            if (!game.meetsCondition(condition)) {
                return false;
            }
        }
        for (Condition condition : trigger.getNo()) {
            if (game.meetsCondition(condition)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return title + '\n' + description + '\n' + options;
    }
}
