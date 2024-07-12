package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.story.StoryNote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import static com.infernal_crew.mini_politics.utils.UICommon.addHighlightedText;
import static com.infernal_crew.mini_politics.utils.UICommon.addNormalText;

public class StoryController extends BarController {
    @FXML
    private ListView<StoryNote> storyListView;
    @FXML
    private TextFlow title;
    @FXML
    private TextFlow description;

    @Override
    public void update() {
        ObservableList<StoryNote> storyNotes = FXCollections.observableArrayList();
        for (StoryNote note : mainController.getGame().getStoryNotes().values()) {
            if (note.isShown()) {
                storyNotes.add(note);
            }
        }
        storyListView.setItems(storyNotes);
        storyListView.setCellFactory(param -> new StoryNoteListCell());

        storyListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showDetails(newValue);
            } else {
                title.getChildren().clear();
                description.getChildren().clear();
            }
        });
    }

    private void showDetails(StoryNote note) {
        title.getChildren().clear();
        description.getChildren().clear();
        addHighlightedText(title, note.getTitle());
        addNormalText(description, note.getDescription());
    }

    static class StoryNoteListCell extends ListCell<StoryNote> {
        @Override
        protected void updateItem(StoryNote item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                TextFlow textFlow = new TextFlow();
                addHighlightedText(textFlow,item.getTitle());
                setGraphic(textFlow);

                CheckBox checkBox = new CheckBox();
                checkBox.setDisable(true);
                checkBox.setStyle("-fx-opacity: 1");
                checkBox.setSelected(item.isDone());
                setGraphic(new HBox(10, checkBox, textFlow));
            }
        }
    }
}
