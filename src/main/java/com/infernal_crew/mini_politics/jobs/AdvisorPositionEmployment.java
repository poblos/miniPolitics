package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class AdvisorPositionEmployment implements Effect {
    private int id;
    private Job position;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Hires a politician";
    }

    @Override
    public boolean handle(Game game) {
        game.getActivePeople().put(id, game.getPeople().get(id));
        game.employ(position, id);
        return true;
    }
}
