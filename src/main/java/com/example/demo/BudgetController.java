package com.example.demo;

import com.example.demo.budget.ExpenseCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class BudgetController {
    @FXML
    private PieChart chart;
    @FXML
    private VBox budgetBox;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (ExpenseCategory category: ExpenseCategory.values()) {
            pieChartData.add(new PieChart.Data(category.toString(),mainController.getGame().getBudget().getExpenses().get(category)));
        }
        chart.setData(pieChartData);
        chart.setTitle("Expenses");
    }
}
