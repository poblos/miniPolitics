package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;

public record LoyaltyChange(String id, int change) implements Effect {
    @Override
    public void handle(Game game){
        float finalChange = game.includeBonus(change, Indicator.PartyCohesion);
        game.updateLoyalty(id, finalChange);
    }
    @Override
    public String toString() {
        return id + ": " + change;
    }
}