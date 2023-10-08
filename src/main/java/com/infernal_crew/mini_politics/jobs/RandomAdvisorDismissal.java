package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class RandomAdvisorDismissal implements Effect {
    @Override
    public String toString() {
        return "Dismisses a politician from the random position";
    }

    @Override
    public boolean handle(Game game) {
        Job job = game.getAllEmployed().keySet().stream().skip(game.getRandom().nextInt(game.getAllEmployed().size())).findFirst().orElse(null);
        game.getActivePeople().remove(game.getAllEmployed().get(job).getId());
        game.getAllEmployed().remove(job);
        return true;
    }
}
