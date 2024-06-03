package com.infernal_crew.mini_politics.controllers;


import com.infernal_crew.mini_politics.policy.Policy;
import com.infernal_crew.mini_politics.policy.PolicyOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PolicyController extends BarController {
    public final ObservableList<Policy> policyNames = FXCollections.observableArrayList();
    public final ObservableList<PolicyOption> optionNames = FXCollections.observableArrayList();

    @FXML
    private ListView<PolicyOption> optionList;

    @FXML
    private ListView<Policy> policyList;

    private Policy displayedPolicy;

    public void update() {
        Map<Integer, Policy> policies = mainController.getGame().getPolicies();

        List<Policy> filteredPolicies = policies.values().stream()
                .filter(policy -> policy.getName() != null)
                .collect(Collectors.toList());

        policyNames.setAll(filteredPolicies);
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

    private static class PolicyCellFactory implements Callback<ListView<Policy>, ListCell<Policy>> {
        @Override
        public ListCell<Policy> call(ListView<Policy> param) {
            return new ListCell<>() {
                @Override
                public void updateItem(Policy policy, boolean empty) {
                    super.updateItem(policy, empty);
                    if (empty || policy == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setGraphic(new OptionLabel(policy.getName()));
                    }
                }
            };
        }

        private static class OptionLabel extends Label {
            public OptionLabel(String option) {
                super(option);
                this.getStyleClass().clear();
                this.getStyleClass().add("listCellLabel");
            }
        }
    }

    private static class OptionCellFactory implements Callback<ListView<PolicyOption>, ListCell<PolicyOption>> {
        @Override
        public ListCell<PolicyOption> call(ListView<PolicyOption> param) {
            return new ListCell<>() {
                @Override
                public void updateItem(PolicyOption option, boolean empty) {
                    super.updateItem(option, empty);
                    if (empty || option == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setGraphic(new OptionLabel(option));
                    }
                }
            };
        }

        private static class OptionLabel extends Label {
            public OptionLabel(PolicyOption option) {
                super(option.getName());
                if (option.isSelected()) {
                    this.getStyleClass().clear();
                    this.getStyleClass().add("currentOptionLabel");

                } else {
                    this.getStyleClass().clear();
                    this.getStyleClass().add("listCellLabel");
                }
            }
        }
    }
}
