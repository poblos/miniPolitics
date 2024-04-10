package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

public class DialogueInvocation implements Effect{
    String id;
    @Override
    public void handle(Game game) {
        game.setDialogueId(id);
    }
}
