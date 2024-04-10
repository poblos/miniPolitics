package com.infernal_crew.mini_politics.media;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class MediaTakeover implements Effect {
    private int id;

    private Affiliation affiliation;

    public int getId() {
        return id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    @Override
    public void handle(Game game) {
        for (MediaGroup group : game.getMediaGroups()) {
            if (group.getId() == id) {
                group.setAffiliation(affiliation);
            }
        }
    }
}
