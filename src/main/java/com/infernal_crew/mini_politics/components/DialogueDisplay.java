package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.controllers.MainController;
import com.infernal_crew.mini_politics.event.Dialogue;
import com.infernal_crew.mini_politics.event.Event;
import com.infernal_crew.mini_politics.event.Option;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class DialogueDisplay extends VBox {
    public DialogueDisplay(Dialogue dialogue, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText(dialogue.getId());
        title.getStyleClass().add("eventTitle");
        this.getChildren().add(title);
        this.getStyleClass().add("event");
        EventButton button = new EventButton(0);
        button.getStyleClass().add("eventButton");
        button.setOnAction(actionEvent -> controller.handleDialogue(button.getClick()));
        button.setText("Skip this dialogue placeholder");
        setVgrow(button, Priority.ALWAYS);
        button.setPrefWidth(600);
        button.setMaxHeight(100);
        button.setWrapText(true);
        this.getChildren().add(button);
    }
}
