package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.IndicatorRelation;

public class LoyaltyCondition implements Condition {
    private String id;
    private IndicatorRelation relation;
    private float value;
    @Override
    public boolean met(Game game) {
        float loyalty = game.getFactions().get(id).getLoyalty();
        if (relation == IndicatorRelation.Higher) {
            return loyalty > value;
        } else {
            return loyalty < value;
        }
    }
}
