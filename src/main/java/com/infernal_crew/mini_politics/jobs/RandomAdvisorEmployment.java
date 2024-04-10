package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class RandomAdvisorEmployment implements Effect {

    @Override
    public void handle(Game game) {
        game.setCurrentPerson(game.getPeople().values().stream().skip(game.getRandom().nextInt(game.getPeople().size())).findFirst().orElse(null));
        assert game.getCurrentPerson() != null;
        game.getActivePeople().put(game.getCurrentPerson().getId(), game.getCurrentPerson());
    }

    @Override
    public String toString() {
        return "Hires a randomised politician for the chosen position";
    }

}
