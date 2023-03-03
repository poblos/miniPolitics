package com.example.demo;

import com.example.demo.event.Event;
import com.example.demo.event.Option;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class EventDisplay extends VBox {
    public EventDisplay(Event event, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText(event.getTitle());
        title.getStyleClass().add("eventTitle");
        this.getChildren().add(title);

        // Image setup
        if (!Objects.equals(event.getGraphic(), null)) {
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/menu_icons/event_background/" + event.getGraphic() + ".png")).toExternalForm());
            this.getChildren().add(new ImageView(image));
        }

        // Desc setup
        TextArea desc = new TextArea();
        desc.setTextFormatter(new TextFormatter<String>(change -> {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
        desc.setText(event.getDescription());
        desc.setWrapText(true);
        desc.setEditable(false);
        desc.setPrefRowCount(7);
        desc.setPrefWidth(600);
        desc.getStyleClass().add("eventDesc");
        this.getChildren().add(desc);

        //Jensons setup
        int i = 0;
        for (Option option : event.getOptions()) {
            EventButton button = new EventButton(i);
            button.setText(option.getDescription());
            button.setOnAction(actionEvent -> controller.handleEvent(button.getClick()));
            button.setPrefWidth(600);
            button.setMaxHeight(100);
            button.setWrapText(true);
            button.getStyleClass().add("eventButton");
            setVgrow(button, Priority.ALWAYS);
            this.getChildren().add(button);
            i++;
        }
        this.getStyleClass().add("event");
    }
}
