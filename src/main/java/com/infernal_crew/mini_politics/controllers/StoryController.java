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
    private TextFlow title;

    @FXML
    private TextFlow description;

    @Override
    public void update() {
        storyBox.getChildren().clear();
        title.getChildren().clear();
        description.getChildren().clear();

        for(StoryNote note : mainController.getGame().getStoryNotes().values()) {
            if(!note.isShown()) continue;
            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.setStyle("-fx-opacity: 1");
            checkBox.setSelected(note.isDone());

            addHighlightedClickableText(storyBox, note.getTitle(), event-> showDetails(note));
            storyBox.getChildren().add(checkBox);

        }
    }

    private void showDetails(StoryNote note) {
        title.getChildren().clear();
        description.getChildren().clear();
        addHighlightedText(title, note.getTitle());
        addNormalText(description, note.getDescription());
    }
}
