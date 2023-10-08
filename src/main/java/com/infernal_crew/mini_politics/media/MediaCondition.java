package com.infernal_crew.mini_politics.media;

import com.infernal_crew.mini_politics.event.Condition;

public class MediaCondition implements Condition {
    private Affiliation affiliation;

    public Affiliation getAffiliation() {
        return affiliation;
    }
}
