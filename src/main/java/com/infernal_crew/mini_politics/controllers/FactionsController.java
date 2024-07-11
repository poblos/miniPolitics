package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import com.infernal_crew.mini_politics.party.Faction;
import com.infernal_crew.mini_politics.utils.UICommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class FactionsController extends BarController {

    @FXML
    private HBox allHBox;

    @FXML
    private TextFlow factionsFlow;

    @Override
    public void update() {
        Game game = mainController.getGame();

        for (Faction faction : game.getFactions().values()) {
            UICommon.addNamedData(factionsFlow, "Name: ", faction.getName());
            UICommon.addNamedData(factionsFlow, "Loyalty: ", Integer.toString(faction.getLoyalty()));
            UICommon.addNamedData(factionsFlow, "Members: ", Integer.toString(faction.getMembers()));
            UICommon.breakLine(factionsFlow);
        }

        UICommon.addNamedData(factionsFlow, "General loyalty: ", Float.toString(game.getIndicatorValue(Indicator.GeneralLoyalty)));
        UICommon.addNamedData(factionsFlow, "Leader authority: ", Float.toString(game.getIndicatorValue(Indicator.Authority)));
    }

    @FXML
    private void onPartyButtonClick(ActionEvent actionEvent) {
        mainController.onPartyButtonClick(actionEvent);
    }
}
