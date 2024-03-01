package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;

    public void update(Game game) {
        textBox.getChildren().clear();

        Text text = new Text(mainController.getGame().getParty().name() + "\n");
        text.getStyleClass().add("partyNameText");
        textBox.getChildren().add(text);

        text = new Text(mainController.getGame().getParty().description() + "\n");
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);

        text = new Text("Ideologies: \n");
        text.getStyleClass().add("partyNameText");
        textBox.getChildren().add(text);

        String ideologies = mainController.getGame().getParty().ideologies().toString();
        text = new Text(ideologies.substring(1,ideologies.length() - 1));
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);
    }
}
