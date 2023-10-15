package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.story.StoryNote;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
            if (note.isDone()) {
                checkBox.setSelected(true);
                storyBox.getChildren().add(new HBox(new TextField(note.getTitle()),checkBox));
            } else {
                checkBox.setSelected(false);
                storyBox.getChildren().add(new HBox(new TextField(note.getTitle()),checkBox));
            }
        }
    }
}
