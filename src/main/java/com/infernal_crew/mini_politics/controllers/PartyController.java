package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.game.Game;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class PartyController extends BarController {
    @FXML
    private TextFlow textBox;
    @FXML
    private HBox allHBox;
    private int currentParty = 0;

    @FXML
    private TextField bottomBar;
    public void initialize() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), allHBox);
        scaleTransition.setFromY(0);
        scaleTransition.setToY(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), allHBox);
        translateTransition.setFromY(-130);
        translateTransition.setToY(0);

        TranslateTransition secondTranslate = new TranslateTransition(Duration.seconds(0.5), bottomBar);
        secondTranslate.setFromY(-250);
        secondTranslate.setToY(0);

        scaleTransition.play();
        translateTransition.play();
        secondTranslate.play();

    }
    public void update() {
        textBox.getChildren().clear();

        Text text = new Text(mainController.getGame().getParties().get(currentParty).name() + "\n");
        text.getStyleClass().add("partyNameText");
        textBox.getChildren().add(text);

        text = new Text(mainController.getGame().getParties().get(currentParty).description() + "\n");
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);

        text = new Text("Ideologies: \n");
        text.getStyleClass().add("partyNameText");
        textBox.getChildren().add(text);

        String ideologies = mainController.getGame().getParties().get(currentParty).ideologies().toString();
        text = new Text(ideologies.substring(1,ideologies.length() - 1));
        text.getStyleClass().add("normalText");
        textBox.getChildren().add(text);
    }

    public void onNextPartyButtonClick() {
        currentParty++;
        currentParty %= mainController.getGame().getParties().size();
        update();
    }
}
