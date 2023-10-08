package com.infernal_crew.mini_politics.indicators;

import com.infernal_crew.mini_politics.event.Effect;

public record IndicatorChange(Indicator indicator, int change) implements Effect {

    @Override
    public String toString() {
        return indicator + ": " + change;
    }
}
