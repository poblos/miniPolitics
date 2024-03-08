package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.budget.ExpenseCategory;
import com.infernal_crew.mini_politics.budget.IncomeCategory;
import com.infernal_crew.mini_politics.game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class BudgetController extends BarController{
    @FXML
    private ToggleGroup incomeOrExpenses;
    @FXML private RadioButton incomeButton;
    @FXML private RadioButton expensesButton;
    @FXML
    private PieChart chart;

    public void update(Game game) {
        if (incomeOrExpenses.getSelectedToggle() == expensesButton) {
            onExpenseButtonClick();
        } else {
            onIncomeButtonClick();
        }
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
