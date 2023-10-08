package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.media.Affiliation;
import com.infernal_crew.mini_politics.media.MediaGroup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;

public class MediaController extends BarController {
    @FXML
    private TextFlow textBox;
    @FXML
    private TilePane mediaBox;

    public void update() {
        mediaBox.getChildren().clear();
        for (MediaGroup group : mainController.getGame().getMediaGroups()) {
            Label newLabel = new Label();
            newLabel.setGraphic(new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/com/infernal_crew/mini_politics/menu_icons/" + group.getName() + ".png")))));
            if (group.getAffiliation() == Affiliation.Government) {
                newLabel.setBorder(Border.stroke(Color.GREEN));
            } else if (group.getAffiliation() == Affiliation.Opposition) {
                newLabel.setBorder(Border.stroke(Color.RED));
            } else {
                newLabel.setBorder(Border.stroke(Color.YELLOW));
            }
            newLabel.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    Text text = new Text("Name: ");
                    text.getStyleClass().add("highlightedText");
                    textBox.getChildren().add(text);

                    text = new Text(group.getName() + "\n");
                    text.getStyleClass().add("normalText");
                    textBox.getChildren().add(text);

                    text = new Text("Type: ");
                    text.getStyleClass().add("highlightedText");
                    textBox.getChildren().add(text);

                    text = new Text(group.getType() + "\n");
                    text.getStyleClass().add("normalText");
                    textBox.getChildren().add(text);

                    text = new Text("Affiliation: ");
                    text.getStyleClass().add("highlightedText");
                    textBox.getChildren().add(text);

                    text = new Text(group.getAffiliation().toString());
                    text.getStyleClass().add("normalText");
                    textBox.getChildren().add(text);

                } else {
                    textBox.getChildren().clear();
                }
            });
            mediaBox.getChildren().add(newLabel);
        }
    }
}
