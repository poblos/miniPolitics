package com.infernal_crew.mini_politics.story;

public class StoryNote {
    private String title;
    private String description;
    private String modifier;
    private boolean done;

    public String getTitle() {
        return title;
    }

    public String getModifier() {
        return modifier;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
