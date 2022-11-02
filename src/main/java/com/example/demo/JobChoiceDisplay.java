package com.example.demo;

import com.example.demo.jobs.Job;
import com.example.demo.jobs.Person;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class JobChoiceDisplay extends VBox {
    public JobChoiceDisplay(Person person, MainController controller) {
        // Title setup
        Label title = new Label();
        title.setText("Choose job");
        title.setFont(new Font("Serif", 28));
        this.getChildren().add(title);

        // Desc setup
        TextArea desc = new TextArea();
        desc.setText("Choose job for:" + person);
        desc.setEditable(false);
        desc.setFont(new Font("Serif", 16));
        desc.setMaxHeight(300);
        this.getChildren().add(desc);

        //Jensons setup
        for (Job job : Job.values()) {
            JobButton button = new JobButton(job);
            button.setText(String.valueOf(job));
            button.setOnAction(actionEvent -> controller.handleJobChoice(button.getJob()));
            button.setPrefWidth(900);
            this.getChildren().add(button);
        }

        this.setStyle("" +
                "        -fx-border-radius:10;" +
                "        -fx-border-width:4;" +
                "-fx-background-color: linear-gradient(from 50px 0px to 50px 50px, lightgrey, deepskyblue)");

    }
}
