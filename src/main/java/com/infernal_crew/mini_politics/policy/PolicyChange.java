package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;
import javafx.util.Pair;

public record PolicyChange(int id, int option) implements Effect {

    @Override
    public void handle(Game game) {
        int total = game.getPolicies().get(id).getTotalOptions();
        int current = game.getPolicies().get(id).getCurrentOption();
        int weight = game.getPolicies().get(id).getWeight();

        float start = (float) current/(total-1);
        float end = (float) option/(total-1);
        game.getPolicies().get(id).setCurrentOption(option);
        game.getPolicyChanges().add(new Pair<>(
                new PolicyChangeInfo(game.getPolicies().get(id).getFeature(), start, end, weight),
                Game.DECISION_RELEVANCE));
    }

}
