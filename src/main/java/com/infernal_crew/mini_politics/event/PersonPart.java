package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PersonPart extends Part{
    private final String name;
    private final String text;
    public PersonPart(String id, String text, boolean followUp, String nextId, String name) {
        super(id, followUp, nextId);
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text + '\n';
    }

    @Override
    public void addStyledText(TextFlow dialogueText) {
        Text desc = new Text(name + ":\n");
        desc.getStyleClass().add("personName");
        dialogueText.getChildren().add(desc);
        desc = new Text(this.getText());
        desc.getStyleClass().add("personPart");
        dialogueText.getChildren().add(desc);
    }
}
