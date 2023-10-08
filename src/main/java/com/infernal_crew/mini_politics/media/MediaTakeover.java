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
    public boolean handle(Game game) {
        game.takeOverMedia(this);
        return true;
    }
}
