package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public class MPTransfer implements Effect {
    String fromID;
    String toID;
    int mpsTransfered;

    @Override
    public void handle(Game game) {
        game.getParties().get(fromID).setSeats(game.getParties().get(fromID).seats() - mpsTransfered);
        game.getParties().get(toID).setSeats(game.getParties().get(toID).seats() + mpsTransfered);
    }
}
