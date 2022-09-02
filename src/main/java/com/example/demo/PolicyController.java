package com.example.demo;

import com.example.demo.policy.Policy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PolicyController {
    public final ObservableList<String> names =
            FXCollections.observableArrayList();
    @FXML private ListView<String> policyList;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void update() {
        for (Policy policy : mainController.getGame().getPolicies().values()) {
            names.add(policy.getName());
        }
        policyList.setItems(names);
    }
}
