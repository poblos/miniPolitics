package com.example.demo;

import com.example.demo.budget.ExpenseCategory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class BudgetController {
    @FXML
    private VBox budgetBox;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update() {
        budgetBox.getChildren().clear();
        for (ExpenseCategory category: ExpenseCategory.values()) {
            budgetBox.getChildren().add(new ExpenseDisplay(category));
        }
        for (Node node : budgetBox.getChildren()) {
            ((ExpenseDisplay) node).setText(((ExpenseDisplay) node).getExpenseCategory() + ": " + mainController.getGame().getBudget().getExpenses().get(((ExpenseDisplay) node).getExpenseCategory()));
        }
    }
}
