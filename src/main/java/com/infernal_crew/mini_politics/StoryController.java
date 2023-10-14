package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.story.StoryNote;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class StoryController extends BarController {

    @FXML
    private VBox storyBox;

    @Override
    public void update() {
        storyBox.getChildren().clear();
        for(StoryNote note : mainController.getGame().getStoryNotes()) {
            if (note.isDone()) {
                storyBox.getChildren().add(new TextField(note.getTitle()));
            }
        }
    }
}
