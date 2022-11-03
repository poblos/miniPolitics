package com.example.demo;

import com.example.demo.event.Event;
import com.example.demo.event.Option;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
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
        desc.setFont(new Font("Serif", 14));
        desc.setPrefRowCount(7);
        desc.setPrefWidth(700);
        this.getChildren().add(desc);

        //Jensons setup
        int i = 0;
        for (Option option : event.getOptions()) {
            EventButton button = new EventButton(i);
            button.setText(option.getDescription());
            button.setOnAction(actionEvent-> controller.handleEvent(button.getClick()));
            button.setPrefWidth(900);
            button.setMaxHeight(100);
            button.setWrapText(true);
            setVgrow(button, Priority.ALWAYS);
            this.getChildren().add(button);
            i++;
        }

        this.setStyle("" +
                "        -fx-border-radius:10;" +
                "        -fx-border-width:4;" +
                "-fx-background-color: linear-gradient(from 50px 0px to 50px 50px, lightgrey, deepskyblue)");
    }
}
