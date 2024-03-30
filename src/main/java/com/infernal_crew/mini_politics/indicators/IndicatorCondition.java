package com.infernal_crew.mini_politics.indicators;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class IndicatorCondition implements Condition {
    private Indicator indicator;
    private IndicatorRelation relation;
    private int value;

    public Indicator getIndicator() {
        return indicator;
    }

    public IndicatorRelation getRelation() {
        return relation;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean met(Game game) {
        if (relation == IndicatorRelation.Higher) {
            return game.getIndicatorValue(indicator) > value;
        } else {
            return game.getIndicatorValue(indicator) < value;
        }
    }
}
