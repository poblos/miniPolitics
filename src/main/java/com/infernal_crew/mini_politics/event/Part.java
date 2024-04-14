package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;

public abstract class Part{
    protected String id;
    protected String text;
    protected boolean followUp;
    protected String nextId;

    public Part(String id, String text, boolean followUp, String nextId) {
        this.id = id;
        this.text = text;
        this.followUp = followUp;
        this.nextId = nextId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isFollowUp() {
        return followUp;
    }

    public String getNextId() {
        return nextId;
    }

    public abstract Text getStyledText();
}
