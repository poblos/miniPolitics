package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.game.Game;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;
    @FXML
    private VBox allVBox;
    public void initialize() {
        allVBox.setScaleX(0);
        allVBox.setTranslateX(-allVBox.getLayoutBounds().getWidth());

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), allVBox);
        scaleTransition.setToX(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), allVBox);
        translateTransition.setFromX(-200);
        translateTransition.setToX(0);

        scaleTransition.play();
        translateTransition.play();

    }
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
