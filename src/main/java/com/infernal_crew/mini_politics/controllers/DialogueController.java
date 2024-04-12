package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javafx.scene.layout.VBox.setVgrow;

public class DialogueController extends AbstractEventController{
    private final Map<String,Part> parts = new HashMap<>();
    @FXML
    private Label title;
    @FXML
    private VBox dialogueText;

    @FXML
    private VBox choicesBox;

    @Override
    public void setEvent(AbstractEvent aEvent) {
        Dialogue dialogue = (Dialogue) aEvent;
        title.setText(dialogue.getId());

        dialogueText.getChildren().clear();
        choicesBox.getChildren().clear();

        for (Part p : dialogue.getParts()) {
            this.parts.put(p.getId(), p);
        }

        Part part = getPart("Starter");
        do {
            TextArea desc = new TextArea();
            desc.setTextFormatter(new TextFormatter<String>(change -> {
                change.setAnchor(change.getCaretPosition());
                return change;
            }));
            desc.setText(part.getText());
            desc.setWrapText(true);
            desc.setEditable(false);

            desc.setPrefWidth(600);
            desc.getStyleClass().add("eventDesc");
            dialogueText.getChildren().add(desc);
            part = getPart(part.getNextId());
            if (part == null) break;
        }
        while (!(part instanceof ChoicePart));

        Button button = new Button();
        button.getStyleClass().add("eventButton");
        button.setOnAction(actionEvent -> mainController.handleDialogue(0));
        button.setText("Skip this dialogue placeholder");


        setVgrow(button, Priority.ALWAYS);
        button.setPrefWidth(600);
        button.setMaxHeight(100);
        button.setWrapText(true);
        choicesBox.getChildren().add(button);
    }

    private Part getPart(String id) {
        return parts.get(id);
    }

}
