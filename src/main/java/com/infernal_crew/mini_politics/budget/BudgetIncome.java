package com.infernal_crew.mini_politics.budget;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public record BudgetIncome(IncomeCategory category, int change) implements Effect {
    @Override
    public boolean handle(Game game) {
        game.getBudget().getIncome().put(category, game.getBudget().getIncome().get(category) + change);
        return true;
    }
}
