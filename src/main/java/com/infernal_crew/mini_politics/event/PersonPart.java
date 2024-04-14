package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;

public class PersonPart extends Part{
    private final String name;
    public PersonPart(String id, String text, boolean followUp, String nextId, String name) {
        super(id, text, followUp, nextId);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Text getStyledText() {
        Text desc = new Text(this.getText());
        desc.getStyleClass().add("personPart");
        return desc;
    }
}
