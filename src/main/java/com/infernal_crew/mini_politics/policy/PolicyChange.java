package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public record PolicyChange(int id, int option) implements Effect {

    @Override
    public boolean handle(Game game) {
        game.getPolicies().get(id).setCurrentOption(option);
        return true;
    }
}
