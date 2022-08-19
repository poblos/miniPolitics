package com.example.demo;

import com.example.demo.budget.ExpenseCategory;
import javafx.scene.control.TextField;

public class ExpenseDisplay extends TextField {
    private final ExpenseCategory expenseCategory;

    public ExpenseDisplay(ExpenseCategory category) {
        this.expenseCategory = category;
        this.setEditable(false);
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }
}
