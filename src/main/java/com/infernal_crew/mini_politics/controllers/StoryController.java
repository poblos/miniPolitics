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

import static com.infernal_crew.mini_politics.utils.UICommon.*;

public class StoryController extends BarController {
    @FXML
    private VBox storyBox;
    @FXML
    private VBox detailsBox;

    @Override
    public void update() {
        storyBox.getChildren().clear();
        for(StoryNote note : mainController.getGame().getStoryNotes().values()) {
            if(!note.isShown()) continue;
            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.setStyle("-fx-opacity: 1");
            checkBox.setSelected(note.isDone());

            TextFlow flow = new TextFlow();
            addHighlightedText(flow, note.getTitle());
            storyBox.getChildren().add(new HBox(flow,checkBox));

            TextFlow flow2 = new TextFlow();
            addHighlightedText(flow2, note.getTitle());
            breakLine(flow2);
            addNormalText(flow2, note.getDescription());
            detailsBox.getChildren().add(new HBox(flow2));
        }
    }
}
