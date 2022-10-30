package com.example.demo;

import com.example.demo.game.Game;
import com.example.demo.jobs.Job;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class JobDisplay extends VBox {
    Label text;
    Label graphic;
    Image image;
    private final Job job;

    public JobDisplay(Job job) {
        this.job = job;
        this.text = new Label();
        this.setAlignment(Pos.CENTER);
        this.graphic = new Label();
        this.text.setText(this.job + ": None");
        try {
            image = new Image("/com/example/demo/menu_icons/people/ModelNotFound.png");
            this.graphic.setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
        this.getChildren().add(graphic);
        this.getChildren().add(text);
        this.setEffect(new DropShadow());
    }

    public Job getJob() {
        return job;
    }

    public void add(Game game) {
        this.text.setText(this.job + ": " + game.getCurrentPerson().getName());
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/menu_icons/people/" +
                    game.getCurrentPerson().getName() + ".png")).toExternalForm());
            this.graphic.setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
    }

    public void update(Game game) {
        if (game.getEmployed(this.job) == null) {
            this.text.setText(this.job + ": None");
            image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/menu_icons/people/ModelNotFound.png")).toExternalForm());
            this.graphic.setGraphic(new ImageView(image));
        }
    }

}

