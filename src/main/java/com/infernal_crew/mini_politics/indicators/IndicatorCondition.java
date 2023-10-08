package com.infernal_crew.mini_politics.indicators;

import com.infernal_crew.mini_politics.event.Condition;

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
}
