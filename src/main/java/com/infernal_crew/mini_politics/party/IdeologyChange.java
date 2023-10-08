package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class IdeologyChange implements Effect {
    Ideology removed;
    Ideology added;

    public Ideology getRemoved() {
        return removed;
    }

    public Ideology getAdded() {
        return added;
    }

    @Override
    public boolean handle(Game game) {
        game.getParty().ideologies().remove(removed);
        game.getParty().ideologies().add(added);
        return true;
    }
}
