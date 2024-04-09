package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class Event extends AbstractEvent{
    private final String description;
    private final List<Option> options;
    private final String graphic;

    public Event(String title, String description, List<Option> options, String graphic) {
        super(title);
        this.description = description;
        this.options = options;
        this.trigger = new Trigger();
        this.graphic = graphic;
    }

    public Event(String title, String description, List<Option> options, String graphic, Trigger trigger, boolean isUnique, boolean isCertain, int probability, List<ProbabilityChanger> probabilityChangers) {
        super(title,trigger,isUnique,isCertain,probability,probabilityChangers);
        this.description = description;
        this.options = options;
        this.graphic = graphic;
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
                    if (!condition.met(game)) {
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
            if (option.getTrigger() == null || option.getTrigger().isMet(game)) {
                newOptions.add(option);
            }
        }
        return new Event(title, description, newOptions, graphic);
    }

    public boolean isEligible(Game game) {
        return trigger == null || trigger.isMet(game);
    }

    @Override
    public String toString() {
        return title + '\n' + description + '\n' + options;
    }
}
