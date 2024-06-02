package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.utils.UICommon;
import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;
    private int currentParty = 0;

    public void update() {
        textBox.getChildren().clear();
        UICommon.addNamedData(textBox, mainController.getGame().getParties().get(currentParty).name() + "\n",
                mainController.getGame().getParties().get(currentParty).description() + "\n");

        String ideologies = mainController.getGame().getParties().get(currentParty).ideologies().toString();
        UICommon.addNamedData(textBox,"Ideologies: \n", ideologies.substring(1, ideologies.length() - 1));
    }

    public void onNextPartyButtonClick() {
        currentParty++;
        currentParty %= mainController.getGame().getParties().size();
        update();
    }
}
