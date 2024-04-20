package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class IdeologyCondition implements Condition {
    Ideology ideology;

    public Ideology getIdeology() {
        return ideology;
    }

    @Override
    public boolean met(Game game) {
        return game.getRulingParty().ideologies().contains(ideology);
    }
}
