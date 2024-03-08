package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.controllers.MainController;
import com.infernal_crew.mini_politics.jobs.Job;
import com.infernal_crew.mini_politics.jobs.Person;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

public class JobChoiceDisplay extends VBox {
    public JobChoiceDisplay(Person person, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText("Choose job");
        title.getStyleClass().add("eventTitle");
        this.getChildren().add(title);

        // Desc setup
        TextArea desc = new TextArea();
        desc.setText("Choose job for:" + person);
        desc.setTextFormatter(new TextFormatter<String>(change -> {
            change.setAnchor(change.getCaretPosition());
            return change;
        }));
        desc.setWrapText(true);
        desc.setEditable(false);
        desc.setPrefRowCount(2);
        desc.setPrefWidth(600);
        desc.getStyleClass().add("eventDesc");
        this.getChildren().add(desc);

        //Jensons setup
        for (Job job : Job.values()) {
            JobButton button = new JobButton(job);
            button.setText(String.valueOf(job));
            button.setOnAction(actionEvent -> controller.handleJobChoice(button.getJob()));
            button.setPrefWidth(600);
            button.setMaxHeight(100);
            button.getStyleClass().add("eventButton");
            this.getChildren().add(button);
        }

        this.getStyleClass().add("event");
    }
}
