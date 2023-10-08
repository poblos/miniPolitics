package com.infernal_crew.mini_politics;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;

    public void update() {
        Text text = new Text("Name: ");
        text.getStyleClass().add("highlightedText");
        textBox.getChildren().add(text);

        text = new Text(mainController.getGame().getParty().name() + "\n");
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);

        text = new Text("Description: ");
        text.getStyleClass().add("highlightedText");
        textBox.getChildren().add(text);

        text = new Text(mainController.getGame().getParty().description() + "\n");
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);

        text = new Text("Ideologies: ");
        text.getStyleClass().add("highlightedText");
        textBox.getChildren().add(text);

        text = new Text(mainController.getGame().getParty().ideologies().toString());
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);
    }
}
