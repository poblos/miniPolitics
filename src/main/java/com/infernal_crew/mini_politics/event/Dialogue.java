package com.infernal_crew.mini_politics.event;


import java.util.List;

public class Dialogue extends AbstractEvent{
    protected Dialogue(String title, Trigger trigger, boolean isUnique, boolean isCertain, int probability, List<ProbabilityChanger> probabilityChanges) {
        super(title, trigger, isUnique, isCertain, probability, probabilityChanges);
    }

    protected Dialogue(String title) {
        super(title);
    }
}
