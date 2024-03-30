package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public record PolicyCondition(int id, int option) implements Condition {

    @Override
    public boolean met(Game game) {
        return game.getPolicies().get(id).getCurrentOption() == option;
    }
}
