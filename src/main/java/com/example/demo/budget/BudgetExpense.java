package com.example.demo.budget;

import com.example.demo.event.Effect;

public record BudgetExpense(ExpenseCategory category, int change) implements Effect {

}
