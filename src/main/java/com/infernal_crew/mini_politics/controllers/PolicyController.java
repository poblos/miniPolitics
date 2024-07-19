package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.policy.Policy;
import com.infernal_crew.mini_politics.policy.PolicyOption;
import com.infernal_crew.mini_politics.population.Feature;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.*;
import java.util.stream.Collectors;

public class PolicyController extends BarController {
    @FXML
    private VBox featureBox;

    @FXML
    private VBox policyBox;

    @FXML
    private ListView<PolicyOption> optionList;

    private int currentOptionIndex = -1;

    public void update() {
        Map<Integer, Policy> policies = mainController.getGame().getPolicies();

        List<Policy> filteredPolicies = policies.values().stream()
                .filter(policy -> policy.getName() != null).toList();

        Map<Feature, List<Policy>> groupedPolicies = filteredPolicies.stream()
                .collect(Collectors.groupingBy(Policy::getFeature));

        policyBox.getChildren().clear();

        for (Map.Entry<Feature, List<Policy>> entry : groupedPolicies.entrySet()) {
            Feature feature = entry.getKey();
            List<Policy> policyList = entry.getValue();

            ListView<Policy> policyListView = new ListView<>(FXCollections.observableArrayList(policyList));
            policyListView.setCellFactory(new PolicyCellFactory());

            policyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldPolicy, newPolicy) -> {
                if (newPolicy != null) {
                    displayPolicyOptions(newPolicy);
                }
            });

            TitledPane titledPane = new TitledPane(feature.name(), policyListView);
            titledPane.setExpanded(false);

            policyBox.getChildren().add(titledPane);
        }
    }

    private void displayPolicyOptions(Policy policy) {
        List<PolicyOption> options = List.of(policy.getOptions());
        currentOptionIndex = policy.getCurrentOption();
        optionList.setItems(FXCollections.observableArrayList(options));
        optionList.setCellFactory(param -> new OptionCell(currentOptionIndex));
    }

    @FXML
    public void initialize() {
        if (optionList != null) {
            optionList.setCellFactory(param -> new OptionCell(currentOptionIndex));
            optionList.getSelectionModel().selectedItemProperty().addListener((observable, oldOption, newOption) -> {
                if (newOption != null) {
                    currentOptionIndex = List.of(optionList.getItems()).indexOf(newOption);
                }
            });
        } else {
            System.err.println("optionList is null. Ensure that the FXML file is correctly loaded and the fx:id matches.");
        }
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

    private static class OptionCell extends ListCell<PolicyOption> {
        private final int currentOptionIndex;

        public OptionCell(int currentOptionIndex) {
            this.currentOptionIndex = currentOptionIndex;
        }

        @Override
        protected void updateItem(PolicyOption option, boolean empty) {
            super.updateItem(option, empty);
            if (empty || option == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(option.getName());
                int index = getIndex();
                if (index == currentOptionIndex) {
                    setStyle("-fx-background-color: #cce5ff; -fx-text-fill: #004085;");
                } else {
                    setStyle("");
                }
            }
        }
    }
}
