package com.example.demo;

import com.example.demo.media.Affiliation;
import com.example.demo.media.MediaGroup;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class MediaController extends BarController{
    @FXML
    private TilePane mediaBox;

    public void update() {
        mediaBox.getChildren().clear();
        for (MediaGroup group : mainController.getGame().getMediaGroups()) {
            Label newLabel = new Label(group.getName() + "\n" + group.getType() + "\n" + group.getAffiliation());
            if (group.getAffiliation() == Affiliation.Government) {
                newLabel.setBackground(Background.fill(Color.BLUE));
            } else if (group.getAffiliation() == Affiliation.Opposition) {
                newLabel.setBackground(Background.fill(Color.RED));
            } else {
                newLabel.setBackground(Background.fill(Color.YELLOW));
            }
            mediaBox.getChildren().add(newLabel);
        }
    }
}
