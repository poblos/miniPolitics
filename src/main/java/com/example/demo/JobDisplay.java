package com.example.demo;

import com.example.demo.game.Game;
import com.example.demo.jobs.Job;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JobDisplay extends Label {
    Image image;
    String name;
    private Job job;

    public JobDisplay(Job job) {
        this.job = job;
        this.setText(this.job + ": None");
        try {
            image = new Image("/ModelNotFound.png");
            setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
    }

    public Job getJob() {
        return job;
    }

    public void add(Game game) {
        this.setText(this.job + ": " + game.getCurrentPerson().getName());
        try {
            image = new Image("/" + game.getCurrentPerson().getName() + ".png");
            setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
    }

    public void update(Game game) {
        if (game.getEmployed(this.job) == null) {
            this.setText(this.job + ": None");
            image = new Image("/ModelNotFound.png");
            setGraphic(new ImageView(image));
        }
    }

}

