package com.infernal_crew.mini_politics.budget;

import com.infernal_crew.mini_politics.event.Effect;
import com.infernal_crew.mini_politics.game.Game;

public record BudgetExpense(ExpenseCategory category, int change) implements Effect {

    @Override
    public boolean handle(Game game) {
        game.getBudget().getExpenses().put(category, game.getBudget().getExpenses().get(category) + change);
        return true;
    }
}
