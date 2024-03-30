package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class SomeAdvisorCondition implements Condition {
    @Override
    public boolean met(Game game) {
        return !game.getEmployed().isEmpty();
    }
}
