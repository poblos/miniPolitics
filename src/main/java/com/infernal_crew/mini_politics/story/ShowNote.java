package com.infernal_crew.mini_politics.story;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class ShowNote implements Effect {
    String id;
    @Override
    public void handle(Game game) {
        game.getStoryNotes().get(id).setShown(true);
    }
}