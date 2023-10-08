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

}
