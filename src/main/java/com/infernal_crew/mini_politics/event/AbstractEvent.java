package com.infernal_crew.mini_politics.event;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractEvent {
    protected final String title;
    protected Trigger trigger;
    protected boolean isUnique;
    protected boolean isCertain;
    protected int probability;
    protected final List<ProbabilityChanger> probabilityChanges;

    protected AbstractEvent(String title, Trigger trigger, boolean isUnique, boolean isCertain, int probability, List<ProbabilityChanger> probabilityChanges) {
        this.title = title;
        this.trigger = trigger;
        this.isUnique = isUnique;
        this.isCertain = isCertain;
        this.probability = probability;
        this.probabilityChanges = probabilityChanges;
    }

    protected AbstractEvent(String title) {
        this.title = title;
        this.isUnique = false;
        this.isCertain = false;
        this.probability = 50;
        this.probabilityChanges = new ArrayList<>();
    }
}
