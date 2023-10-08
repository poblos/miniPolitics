package com.infernal_crew.mini_politics.budget;

import com.infernal_crew.mini_politics.event.Effect;

public record BudgetExpense(ExpenseCategory category, int change) implements Effect {

}
