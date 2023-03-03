package com.example.demo.event;

import com.example.demo.game.Game;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class Event {
    private final String title;
    private final String description;
    private final List<Option> options;
    private final String graphic;
    private final Trigger trigger;
    private final boolean isUnique;
    private final boolean isCertain;
    private int probability;

    private final List<ProbabilityChanger> probabilityChanges;

    public Event(String title, String description, List<Option> options, String graphic) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = new Trigger();
        this.isUnique = false;
        this.isCertain = false;
        this.probability = 50;
        this.probabilityChanges = new ArrayList<>();
        this.graphic = graphic;
    }

    public Event(String title, String description, List<Option> options, String graphic, Trigger trigger, boolean onlyOnce, boolean isCertain, int probability, List<ProbabilityChanger> probabilityChangers) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.graphic = graphic;
        this.trigger = trigger;
        this.isUnique = onlyOnce;
        this.isCertain = isCertain;
        this.probability = probability;
        this.probabilityChanges = probabilityChangers;
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

    public int getProbability(Game game) {
        int sum = probability;
        if (probability == 0) {
            sum = 50;
        }

        if (probabilityChanges != null) {
            for(ProbabilityChanger changer : probabilityChanges) {
                boolean breaker = true;
                for(Condition condition : changer.conditions()) {
                    if (!game.meetsCondition(condition)) {
                        breaker = false;
                        break;
                    }
                }
                if (breaker) {
                    sum+= changer.probChange();
                }
            }
        }
        return max(0,sum);
    }

    public String getGraphic() {
        return graphic;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public boolean isCertain() {
        return isCertain;
    }

    public Event adjust(Game game) {
        ArrayList<Option> newOptions = new ArrayList<>();
        for (Option option : options) {
            if (game.meetsConditions(option)) {
                newOptions.add(option);
            }
        }
        return new Event(title, description, newOptions, graphic);
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
