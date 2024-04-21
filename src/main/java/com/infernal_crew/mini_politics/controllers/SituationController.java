package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.policy.Policy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.util.Callback;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SituationController extends BarController {
    public final ObservableList<Policy> situationNames = FXCollections.observableArrayList();
    @FXML
    private ListView<Policy> situationList;
    @FXML
    private Label descriptionLabel;
    private Policy displayedSituation;

    public void update() {
        Map<Integer, Policy> situations = mainController.getGame().getPolicies();

        List<Policy> filteredSituations = situations.values().stream()
                .filter(situation -> situation.getName() == null)
                .collect(Collectors.toList());

        situationNames.setAll(filteredSituations);
        situationList.setItems(situationNames);
    }

    public void initialize() {
        situationList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Policy> call(ListView<Policy> param) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Policy situation, boolean empty) {
                        super.updateItem(situation, empty);
                        if (empty || situation == null) {
                            setText(null);
                        } else {
                            setText(situation.getOptions()[situation.getCurrentOption()].getName());
                        }
                    }
                };
            }
        });

        situationList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, current) -> {
            displayedSituation = current;
            if (current != null) {
                descriptionLabel.setText(current.getOptions()[current.getCurrentOption()].getDesc());
            } else {
                descriptionLabel.setText("");
            }
        });
    }
}
