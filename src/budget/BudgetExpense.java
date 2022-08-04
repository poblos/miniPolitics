package budget;

import events.Effect;

public record BudgetExpense(ExpenseCategory category, int change) implements Effect {

}
