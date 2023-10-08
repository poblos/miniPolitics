package com.infernal_crew.mini_politics.budget;

import com.infernal_crew.mini_politics.event.Effect;

public record BudgetIncome(IncomeCategory category, int change) implements Effect {
}
