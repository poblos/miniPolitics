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
    private TextFlow nameFlow;

    @FXML
    private TextFlow loyaltyFlow;

    @FXML
    private TextFlow memberFlow;

    @Override
    public void update() {
        Game game = mainController.getGame();
        nameFlow.getChildren().clear();
        loyaltyFlow.getChildren().clear();
        memberFlow.getChildren().clear();

        for (Faction faction : game.getFactions().values()) {
            UICommon.addNamedData(nameFlow, "Name: ", faction.getName());
            UICommon.addNamedData(loyaltyFlow, "Loyalty: ", Float.toString(faction.getLoyalty()));
            UICommon.addNamedData(memberFlow, "Members: ", Integer.toString(faction.getMembers()));
            UICommon.breakLine(nameFlow);
            UICommon.breakLine(loyaltyFlow);
            UICommon.breakLine(memberFlow);
        }

        UICommon.addNamedData(nameFlow, "Party cohesion: ", Float.toString(game.getIndicatorValue(Indicator.PartyCohesion)));
    }

    @FXML
    private void onPartyButtonClick(ActionEvent actionEvent) {
        mainController.onPartyButtonClick(actionEvent);
    }
}
