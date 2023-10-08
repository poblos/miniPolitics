package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class AdvisorEmployment implements Effect {
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Hires a politician";
    }

    @Override
    public boolean handle(Game game) {
        game.setCurrentPerson(game.getPeople().get(id));
        game.getActivePeople().put(game.getCurrentPerson().getId(), game.getCurrentPerson());
        return false;
    }
}
