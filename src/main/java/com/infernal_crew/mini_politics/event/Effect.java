package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

public interface Effect {
    boolean handle(Game game);
}

