package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.budget.ExpenseCategory;
import com.infernal_crew.mini_politics.budget.IncomeCategory;
import com.infernal_crew.mini_politics.utils.UICommon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class BudgetController extends BarController{
    @FXML
    private ToggleGroup incomeOrExpenses;
    @FXML private ToggleButton incomeButton;
    @FXML private ToggleButton expensesButton;
    @FXML private TextFlow incomeFlow;
    @FXML private TextFlow expenseFlow;
    @FXML
    private PieChart chart;

    public void update() {
        expenseFlow.getChildren().clear();
        incomeFlow.getChildren().clear();
        updateIncomeLabel();
        updateExpenseLabel();
        if (incomeOrExpenses.getSelectedToggle() == expensesButton) {
            onExpenseButtonClick();
        } else {
            onIncomeButtonClick();
        }
    }

    private void updateExpenseLabel() {
        UICommon.addNamedData(expenseFlow,"Total expenses: ", Integer.toString(mainController.getGame().getBudget().getExpensesSum()));
    }

    private void updateIncomeLabel() {
        UICommon.addNamedData(incomeFlow,"Total income: ", Integer.toString(mainController.getGame().getBudget().getIncomeSum()));
    }

    public void onExpenseButtonClick() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (ExpenseCategory category : ExpenseCategory.values()) {
            pieChartData.add(new PieChart.Data(category.toString(), mainController.getGame().getBudget().getExpenses().get(category)));
            chart.setTitle("Expenses");
        }
        chart.setData(pieChartData);
    }

    public void onIncomeButtonClick() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (IncomeCategory category : IncomeCategory.values()) {
            pieChartData.add(new PieChart.Data(category.toString(), mainController.getGame().getBudget().getIncome().get(category)));
            chart.setTitle("Income");
        }
        chart.setData(pieChartData);
    }
}
