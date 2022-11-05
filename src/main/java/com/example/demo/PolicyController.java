package com.example.demo;

import com.example.demo.policy.Policy;
import com.example.demo.policy.PolicyOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.Arrays;

class PolicyCellFactory implements Callback<ListView<Policy>, ListCell<Policy>> {

    @Override
    public ListCell<Policy> call(ListView<Policy> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(Policy policy, boolean empty) {
                super.updateItem(policy, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (policy != null) {
                    setText(null);
                    setGraphic(new Label(policy.getName()));
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
        };
    }

}

class OptionCellFactory implements Callback<ListView<PolicyOption>, ListCell<PolicyOption>> {
    private static class SelectPolicyLabel extends Label {
        public SelectPolicyLabel(String name) {
            super(name);
            this.setStyle("-fx-background-color: linear-gradient(from 25px 1px to 100px 100px,#00A230, #F9F9F9)");
        }
    }

    @Override
    public ListCell<PolicyOption> call(ListView<PolicyOption> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(PolicyOption option, boolean empty) {
                super.updateItem(option, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (option != null) {
                    setText(null);
                    if (!option.isSelected()) {
                        setGraphic(new Label(option.getName()));
                    } else {
                        setGraphic((new SelectPolicyLabel(option.getName())));
                    }
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
        };
    }

}

public class PolicyController extends BarController {
    public final ObservableList<Policy> policyNames =
            FXCollections.observableArrayList();

    public final ObservableList<PolicyOption> optionNames =
            FXCollections.observableArrayList();
    @FXML
    private ListView<PolicyOption> optionList;
    @FXML
    private ListView<Policy> policyList;
    private Policy displayedPolicy;

    public void update() {
        policyNames.clear();
        policyNames.addAll(mainController.getGame().getPolicies().values());
        policyList.setItems(policyNames);
        policyList.setItems(policyNames);
        if (displayedPolicy != null) {
            optionNames.clear();
            int i = 0;
            for (PolicyOption option : displayedPolicy.getOptions()) {
                option.setSelected(i == displayedPolicy.getCurrentOption());
                i++;
            }

            optionNames.addAll(Arrays.asList(displayedPolicy.getOptions()));
            optionList.setItems(optionNames);
        }
    }

    public void initialize() {
        policyList.setCellFactory(new PolicyCellFactory());
        optionList.setCellFactory(new OptionCellFactory());
        policyList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, current) -> {
            optionNames.clear();

            if (current != null) {
                int i = 0;
                for (PolicyOption option : current.getOptions()) {
                    option.setSelected(i == current.getCurrentOption());
                    i++;
                }
                optionNames.addAll(Arrays.asList(current.getOptions()));
                optionList.setItems(optionNames);
                displayedPolicy = current;
            }
        });
    }
}
