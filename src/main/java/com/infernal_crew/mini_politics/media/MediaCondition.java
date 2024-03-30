package com.infernal_crew.mini_politics.media;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class MediaCondition implements Condition {
    private Affiliation affiliation;

    public Affiliation getAffiliation() {
        return affiliation;
    }

    @Override
    public boolean met(Game game) {
        return game.hasAffiliated(affiliation);
    }
}
