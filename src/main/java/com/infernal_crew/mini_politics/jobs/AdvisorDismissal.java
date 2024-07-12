package com.infernal_crew.mini_politics.jobs;
import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;

import static com.infernal_crew.mini_politics.game.Game.ADVISOR_COOLDOWN;

public class AdvisorDismissal implements Effect {
    Job job;

    @Override
    public String toString() {
        return "Fires a politician occupying the mentioned position";
    }

    public Job getJob() {
        return job;
    }

    @Override
    public void handle(Game game) {
        game.dismiss(job);
        game.getAllEmployed().remove(job);
    }
}
