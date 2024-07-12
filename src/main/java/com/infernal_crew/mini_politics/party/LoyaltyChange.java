package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import com.infernal_crew.mini_politics.jobs.Job;
import com.infernal_crew.mini_politics.jobs.TraitEffect;

public record LoyaltyChange(String id, int change) implements Effect, TraitEffect {
    @Override
    public void handle(Game game){
        float finalChange = game.includeBonus(change, Indicator.PartyCohesion);
        game.updateLoyalty(id, finalChange);
    }

    @Override
    public int calculateBonus(Job job, Indicator indicator) {
        return 0;
    }

    @Override
    public String toString() {
        return id + ": " + change;
    }
}