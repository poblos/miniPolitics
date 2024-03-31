package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public record AdvisorSkillCondition(Job job, String trait) implements Condition {
    @Override
    public boolean met(Game game) {
        return game.getEmployed().containsKey(job) && game.getEmployed().get(job).getTraits().contains(trait);
    }
}

