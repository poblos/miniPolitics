package com.infernal_crew.mini_politics.media;

import com.infernal_crew.mini_politics.event.Effect;

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
