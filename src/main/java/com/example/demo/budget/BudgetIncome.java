package com.example.demo.budget;

import com.example.demo.event.Effect;

public record BudgetIncome(IncomeCategory category, int change) implements Effect {
}
