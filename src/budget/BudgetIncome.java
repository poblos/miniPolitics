package budget;

import events.Effect;

public record BudgetIncome(IncomeCategory category, int change) implements Effect {
}
