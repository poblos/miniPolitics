package com.infernal_crew.mini_politics.modifiers;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class ModifierRemoval implements Effect {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " will be removed from active modifiers";
    }

    @Override
    public boolean handle(Game game) {
        game.removeModifier(name);
        return true;
    }
}
