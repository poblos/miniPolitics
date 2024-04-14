package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.event.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.layout.VBox.setVgrow;

public class DialogueController extends AbstractEventController {
    private final Map<String, Part> parts = new HashMap<>();
    private Part currentPart;
    @FXML
    private Label title;
    @FXML
    private TextFlow dialogueText;

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

        currentPart = getPart("Starter");
        addPart();

    }

    private void addPart() {
        choicesBox.getChildren().clear();
        dialogueText.getChildren().add(currentPart.getStyledText());

        currentPart = getPart(currentPart.getNextId());

        if (!(currentPart instanceof ChoicePart choicePart)) {
            if (currentPart != null) {
                cont();
            } else {
                end();
            }
        } else {
            choicePart = choicePart.adjust(mainController.getGame());
            for (Option option : choicePart.getOptions()) {
                addButton(option.getDescription(), actionEvent -> {
                    mainController.getGame().handleOption(option);
                    addPart();
                });
            }
        }
    }

    private void end() {
        addButton("End", actionEvent -> mainController.endDialogue());
    }

    private void cont() {
        addButton("Continue", actionEvent -> addPart());
    }

    private void addButton(String buttonText, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button();
        button.getStyleClass().add("eventButton");
        button.setOnAction(eventHandler);
        button.setText(buttonText);

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
