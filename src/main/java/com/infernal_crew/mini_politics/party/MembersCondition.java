package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.IndicatorRelation;

public class MembersCondition implements Condition {
    private String id;
    private IndicatorRelation relation;
    private int count;
    @Override
    public boolean met(Game game) {
        float loyalty = game.getFactions().get(id).getMembers();
        if (relation == IndicatorRelation.Higher) {
            return loyalty > count;
        } else {
            return loyalty < count;
        }
    }
}
