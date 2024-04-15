package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class ChoicePart extends Part{
    private final List<DialogueOption> options;
    public ChoicePart(String id, boolean followUp, String nextId, List<DialogueOption> options) {
        super(id, followUp, nextId);
        this.options = options;
    }

    public ChoicePart adjust(Game game) {
        ArrayList<DialogueOption> newOptions = new ArrayList<>();
        for (DialogueOption option : options) {
            if (option.getTrigger() == null || option.getTrigger().isMet(game)) {
                newOptions.add(option);
            }
        }
        return new ChoicePart(id, followUp, nextId, newOptions);
    }

    public List<DialogueOption> getOptions() {
        return options;
    }

    @Override
    public void addStyledText(TextFlow dialogueText) {

    }
}
