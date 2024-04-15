package com.infernal_crew.mini_politics.event;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public abstract class Part{
    protected String id;
    protected boolean followUp;
    protected String nextId;

    public Part(String id, boolean followUp, String nextId) {
        this.id = id;
        this.followUp = followUp;
        this.nextId = nextId;
    }

    public String getId() {
        return id;
    }

    public boolean isFollowUp() {
        return followUp;
    }

    public String getNextId() {
        return nextId;
    }

    public abstract void addStyledText(TextFlow dialogueText);
}
