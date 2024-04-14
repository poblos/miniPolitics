package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;

public class DescriptionPart extends Part{
    public DescriptionPart(String id, String text, boolean followUp, String nextId) {
        super(id, text, followUp, nextId);
    }

    public Text getStyledText() {
        Text desc = new Text(this.getText());
        desc.getStyleClass().add("descPart");
        return desc;
    }

}
