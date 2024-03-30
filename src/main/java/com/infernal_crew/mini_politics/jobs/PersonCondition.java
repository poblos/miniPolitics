package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class PersonCondition implements Condition {
    private int id;

    public int getId() {
        return id;
    }


    @Override
    public boolean met(Game game) {
        return game.getActivePeople().containsKey(id);
    }
}
