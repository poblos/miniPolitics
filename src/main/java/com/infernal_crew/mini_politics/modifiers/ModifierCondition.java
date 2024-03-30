package com.infernal_crew.mini_politics.modifiers;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class ModifierCondition implements Condition {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean met(Game game) {
        return game.getActiveModifiers().containsKey(name);
    }
}
