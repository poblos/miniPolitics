package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.event.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    @FXML
    private ScrollPane scroller;

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
        addPart(currentPart.getNextId());

    }

    private void addPart(String nextId) {
        choicesBox.getChildren().clear();
        currentPart.addStyledText(dialogueText);
        scroller.setVvalue(1.0);

        currentPart = getPart(nextId);

        if (!(currentPart instanceof ChoicePart choicePart)) {
            if (currentPart != null) {
                cont(currentPart.getNextId());
            } else {
                end();
            }
        } else {
            choicePart = choicePart.adjust(mainController.getGame());
            for (DialogueOption option : choicePart.getOptions()) {
                addButton(option.getDescription(), actionEvent -> {
                    mainController.getGame().handleOption(option);
                    mainController.updateUpperBar();
                    option.addStyledText(dialogueText);
                    scroller.setVvalue(1.0);
                    addPart(option.getNextId());
                });
            }
        }
    }

    private void end() {
        addButton("End", actionEvent -> mainController.endDialogue());
    }

    private void cont(String nextId) {
        addButton("Continue", actionEvent -> addPart(nextId));
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
