package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class AdvisorCondition implements Condition {
    private Job job;

    public Job getJob() {
        return job;
    }

    @Override
    public boolean met(Game game) {
        return game.getEmployed().containsKey(job);
    }
}
