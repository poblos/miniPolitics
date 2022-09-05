package com.example.demo;

import com.example.demo.media.MediaGroup;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class MediaController extends BarController{
    @FXML
    private VBox mediaBox;

    public void update() {
        mediaBox.getChildren().clear();
        for (MediaGroup group : mainController.getGame().getMediaGroups()) {
            mediaBox.getChildren().add(new TextArea(group.getName() + "\n" + group.getType() + "\n" + group.getAffiliation()));
        }

        for (Node area : mediaBox.getChildren()) {
            ((TextArea) area).setEditable(false);
        }
    }
}
