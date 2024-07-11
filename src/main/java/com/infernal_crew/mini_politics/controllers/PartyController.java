package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.components.ParliamentDotPlot;
import com.infernal_crew.mini_politics.party.Party;
import com.infernal_crew.mini_politics.utils.UICommon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.util.Collection;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;
    @FXML
    private VBox parliamentContainer;
    private ParliamentDotPlot parliamentDotPlot;

    @FXML
    public void initialize() {
        parliamentDotPlot = new ParliamentDotPlot(360, 15, 25, 350, 350, 50, 5);
        parliamentDotPlot.setOnSeatHover(this::showPartyDescription);
        parliamentContainer.getChildren().add(parliamentDotPlot);
    }

    public void update() {
        Collection<Party> parties = mainController.getGame().getParties().values();
        parliamentDotPlot.updatePlot(parties.stream().sorted().toList());
    }

    private void showPartyDescription(Party party) {
        textBox.getChildren().clear();
        UICommon.addNamedData(textBox, party.name() + "\n", party.description() + "\n");

        String ideologies = party.ideologies().toString();
        UICommon.addNamedData(textBox, "Ideologies: \n", ideologies.substring(1, ideologies.length() - 1));
    }

    @FXML
    private void onFactionsButtonClick(ActionEvent actionEvent) {
        mainController.onFactionsButtonClick(actionEvent);
    }
}
