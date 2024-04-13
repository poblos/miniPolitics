package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.event.AbstractEvent;
import com.infernal_crew.mini_politics.event.Event;
import com.infernal_crew.mini_politics.event.Option;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;

import static javafx.scene.layout.VBox.setVgrow;

public class EventController extends AbstractEventController {
    @FXML
    private Label title;
    @FXML
    private ImageView image;
    @FXML
    private TextFlow description;
    @FXML
    private VBox options;

    @Override
    public void setEvent(AbstractEvent aEvent) {
        Event event = (Event) aEvent;
        title.setText(event.getTitle());
        if (!Objects.equals(event.getGraphic(), null)) {
            Image im = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/event_background/" + event.getGraphic() + ".png")).toExternalForm());
            image.setImage(im);
        }
        Text text = new Text(event.getDescription());
        text.getStyleClass().add("eventDesc");
        description.getChildren().add(text);

        int i = 0;
        for (Option option : event.getOptions()) {
            Button button = new Button();
            button.setText(option.getDescription());
            int finalI = i;
            button.setOnAction(actionEvent -> mainController.handleEvent(finalI));
            button.setPrefWidth(600);
            button.setMaxHeight(100);
            button.setWrapText(true);
            button.getStyleClass().add("eventButton");
            setVgrow(button, Priority.ALWAYS);
            options.getChildren().add(button);
            i++;
        }
    }
}
