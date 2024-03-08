package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.controllers.MainController;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.jobs.Job;
import com.infernal_crew.mini_politics.jobs.Person;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class JobDisplay extends VBox {
    Label text;
    Button button;
    Image image;
    private final Job job;

    public JobDisplay(Job job, MainController controller) {
        this.job = job;
        this.text = new Label();
        this.setAlignment(Pos.CENTER);
        this.button = new Button();
        button.getStyleClass().add("indicatorButton");
        button.setOnAction(actionEvent -> controller.showPeopleList(job));
        this.text.setText(this.job + ": None");
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/people/ModelNotFound.png")).toExternalForm());
            button.setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
        this.getChildren().add(button);
        this.getChildren().add(text);
        this.setEffect(new DropShadow());
    }

    public Job getJob() {
        return job;
    }

    public void update(Game game) {
        Person p = game.getEmployed(this.job);
        if (p == null) {
            this.text.setText(this.job + ": None");
            image = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/people/ModelNotFound.png")).toExternalForm());
            this.button.setGraphic(new ImageView(image));
        } else {
            this.text.setText(this.job + ": " + p.getName());
            try {
                image = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/people/" +
                        p.getName() + ".png")).toExternalForm());
                this.button.setGraphic(new ImageView(image));
            } catch (Exception e) {
                System.out.println("problem with loading a person's image");
            }
        }
    }

}