package com.infernal_crew.mini_politics.media;
import com.infernal_crew.mini_politics.event.Condition;

public class MediaIdCondition implements Condition {
    private Affiliation affiliation;
    private int id;
    public Affiliation getAffiliation() {
        return affiliation;
    }

    public int getId() {
        return id;
    }
}

