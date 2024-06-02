package com.infernal_crew.mini_politics.budget;

import java.util.Map;

public class Budget {
    Map<ExpenseCategory, Integer> expenses;
    Map<IncomeCategory, Integer> income;

    public Map<ExpenseCategory, Integer> getExpenses() {
        return expenses;
    }

    public Map<IncomeCategory, Integer> getIncome() {
        return income;
    }

    public int getExpensesSum() {
        return expenses.values().stream().reduce(0, Integer::sum);
    }

    public int getIncomeSum() {
        return income.values().stream().reduce(0, Integer::sum);
    }
}
