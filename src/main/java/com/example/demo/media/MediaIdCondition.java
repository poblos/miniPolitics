package com.example.demo.media;
import com.example.demo.event.Condition;

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

