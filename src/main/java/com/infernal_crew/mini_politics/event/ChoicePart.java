package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

import java.util.ArrayList;
import java.util.List;

public class ChoicePart extends Part{
    private final List<Option> options;
    public ChoicePart(String id, String text, boolean followUp, String nextId, List<Option> options) {
        super(id, text, followUp, nextId);
        this.options = options;
    }

    public ChoicePart adjust(Game game) {
        ArrayList<Option> newOptions = new ArrayList<>();
        for (Option option : options) {
            if (option.getTrigger() == null || option.getTrigger().isMet(game)) {
                newOptions.add(option);
            }
        }
        return new ChoicePart(id, text, followUp, nextId, newOptions);
    }

    public List<Option> getOptions() {
        return options;
    }
}
