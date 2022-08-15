package com.example.demo.event;

import com.example.demo.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String title;
    private final String description;
    private final List<Option> options;
    private final Trigger trigger;
    private final boolean isUnique;
    private int probability;

    private final List<ProbabilityChanger> probabilityChangers;

    public Event(String title, String description, List<Option> options) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = new Trigger();
        this.isUnique = false;
        this.probability = 50;
        this.probabilityChangers = new ArrayList<>();
    }

    public Event(String title, String description, List<Option> options, Trigger trigger, boolean onlyOnce, int probability, List<ProbabilityChanger> probabilityChangers) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = trigger;
        this.isUnique = onlyOnce;
        this.probability = probability;
        this.probabilityChangers = probabilityChangers;
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

    public List<ProbabilityChanger> getProbabilityChangers() {
        return probabilityChangers;
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

    public boolean isUnique() {
        return isUnique;
    }
}
