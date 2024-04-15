package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DescriptionPart extends Part{
    private String text;
    public DescriptionPart(String id, String text, boolean followUp, String nextId) {
        super(id, followUp, nextId);
        this.text = text;
    }

    public String getText() {
        return text + '\n';
    }

    public void addStyledText(TextFlow dialogueText) {
        Text desc = new Text(this.getText());
        desc.getStyleClass().add("descPart");
        dialogueText.getChildren().add(desc);
    }

}
