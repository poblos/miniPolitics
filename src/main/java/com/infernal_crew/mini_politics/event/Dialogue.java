package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.utils.Identifiable;

import java.util.List;

public class Dialogue extends AbstractEvent implements Identifiable {
    private String id;

    private Part[] parts;
    protected Dialogue(String title, Trigger trigger, boolean isUnique, boolean isCertain, int probability,
                       List<ProbabilityChanger> probabilityChanges, String id, Part[] parts) {
        super(title, trigger, isUnique, isCertain, probability, probabilityChanges);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Part[] getParts() {
        return parts;
    }

    public String getTitle() {
        return title;
    }
}
