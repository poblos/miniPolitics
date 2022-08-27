package com.example.demo;

import com.example.demo.event.Event;
import com.example.demo.event.Option;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EventDisplay extends VBox {
    public EventDisplay(Event event, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText(event.getTitle());
        title.setFont(new Font("Serif", 28));

        this.getChildren().add(title);
        // Desc setup
        TextArea desc = new TextArea();
        desc.setText(event.getDescription());
        desc.setWrapText(true);
        desc.setEditable(false);
        desc.setFont(new Font("Serif", 16));
        this.getChildren().add(desc);

        //Jensons setup

        int i = 0;
        for (Option option : event.getOptions()) {
            EventButton button = new EventButton(i);
            button.setText(option.getDescription());
            button.setOnAction(actionEvent-> controller.handleEvent(button.getClick()));
            this.getChildren().add(button);
            i++;
        }

    }
}