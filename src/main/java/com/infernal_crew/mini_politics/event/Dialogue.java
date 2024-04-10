package com.infernal_crew.mini_politics.event;

import java.util.List;

public class Dialogue extends AbstractEvent{
    private String id;

    private Part[] parts;
    protected Dialogue(String title, Trigger trigger, boolean isUnique, boolean isCertain, int probability,
                       List<ProbabilityChanger> probabilityChanges, String id, Part[] parts) {
        super(title, trigger, isUnique, isCertain, probability, probabilityChanges);
        this.id = id;
    }

    protected Dialogue(String title) {
        super(title);
    }

    public String getId() {
        return id;
    }

    public Part[] getParts() {
        return parts;
    }
}
