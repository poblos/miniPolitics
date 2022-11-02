package com.example.demo.media;

import com.example.demo.event.Effect;

public class MediaTakeover implements Effect {
    private int id;

    private Affiliation affiliation;

    public int getId() {
        return id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }
}
