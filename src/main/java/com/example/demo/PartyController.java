package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class PartyController extends BarController{

    @FXML
    public TextArea name;
    @FXML
    public TextArea history;
    @FXML
    public TextArea ideologies;

    public void update() {
        name.setText("name: " + mainController.getGame().getParty().name());
        history.setText("history: " + mainController.getGame().getParty().description());
        ideologies.setText("ideologies: " + mainController.getGame().getParty().ideologies());
    }
}
