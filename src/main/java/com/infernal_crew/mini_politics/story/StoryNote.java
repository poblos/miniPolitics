package com.infernal_crew.mini_politics.story;

import com.infernal_crew.mini_politics.utils.Identifiable;

public class StoryNote implements Identifiable {
    private String id;
    private String title;
    private String description;
    private String modifier;
    private boolean done;
    private boolean shown;

    public String getId() {
        return id;
    }

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

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
