package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.story.StoryNote;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class StoryController extends BarController {

    @FXML
    private VBox storyBox;

    @Override
    public void update() {
        storyBox.getChildren().clear();
        for(StoryNote note : mainController.getGame().getStoryNotes()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.setStyle("-fx-opacity: 1");
            checkBox.setSelected(note.isDone());
            Text text = new Text(note.getTitle());
            text.getStyleClass().add("highlightedText");
            TextFlow flow = new TextFlow();
            flow.getChildren().add(text);
            storyBox.getChildren().add(new HBox(text,checkBox));
        }
    }
}
