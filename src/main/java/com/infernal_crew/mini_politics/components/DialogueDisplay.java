package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.controllers.MainController;
import com.infernal_crew.mini_politics.event.Dialogue;
import com.infernal_crew.mini_politics.event.Event;
import com.infernal_crew.mini_politics.event.Option;
import com.infernal_crew.mini_politics.event.Part;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DialogueDisplay extends VBox {
    private final Map<String,Part> parts = new HashMap<>();
    public DialogueDisplay(Dialogue dialogue, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText(dialogue.getId());
        title.getStyleClass().add("eventTitle");
        this.getChildren().add(title);
        this.getStyleClass().add("event");

        for (Part p : dialogue.getParts()) {
            this.parts.put(p.getId(), p);
        }
        Part start = getPart("Starter");
        TextArea desc = new TextArea();
        desc.setTextFormatter(new TextFormatter<String>(change -> {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
        desc.setText(start.getText());
        desc.setWrapText(true);
        desc.setEditable(false);
        desc.setPrefRowCount(7);
        desc.setPrefWidth(600);
        desc.getStyleClass().add("eventDesc");
        this.getChildren().add(desc);

        Button button = new Button();
        button.getStyleClass().add("eventButton");
        button.setOnAction(actionEvent -> controller.handleDialogue(0));
        button.setText("Skip this dialogue placeholder");


        setVgrow(button, Priority.ALWAYS);
        button.setPrefWidth(600);
        button.setMaxHeight(100);
        button.setWrapText(true);
        this.getChildren().add(button);
    }

    private Part getPart(String id) {
        return parts.get(id);
    }
}
