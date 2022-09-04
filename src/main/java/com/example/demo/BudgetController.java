package com.example.demo;

import com.example.demo.budget.ExpenseCategory;
import com.example.demo.budget.IncomeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class BudgetController {
    @FXML
    private ToggleGroup incomeOrExpenses;
    @FXML private RadioButton incomeButton;
    @FXML private RadioButton expensesButton;
    @FXML
    private PieChart chart;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update() {
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
