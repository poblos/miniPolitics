package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class DialogueOption extends Option{
    private String nextId;
    public DialogueOption(String description, List<Effect> effects) {
        super(description, effects);
    }

    public DialogueOption(String description, List<Effect> effects, Trigger trigger) {
        super(description, effects, trigger);
    }

    public String getNextId() {
        return nextId;
    }

    public void addStyledText(TextFlow dialogueText) {
        Text desc = new Text("YOU" + ":\n");
        desc.getStyleClass().add("yourName");
        dialogueText.getChildren().add(desc);
        desc = new Text(getDescription() + '\n');
        desc.getStyleClass().add("personPart");
        dialogueText.getChildren().add(desc);
    }
}
