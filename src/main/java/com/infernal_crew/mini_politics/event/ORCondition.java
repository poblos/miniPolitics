package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

import java.util.List;

public class ORCondition implements Condition{
    List<Condition> conditions;
    @Override
    public boolean met(Game game) {
        return conditions.stream().anyMatch(condition -> condition.met(game));
    }
}
