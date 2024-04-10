package com.infernal_crew.mini_politics.indicators;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public record IndicatorChange(Indicator indicator, int change) implements Effect {
    @Override
    public void handle(Game game){
        float finalChange = game.includeBonus(change, indicator);
        game.updateIndicator(finalChange, indicator);
    }
    @Override
    public String toString() {
        return indicator + ": " + change;
    }
}
