package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class IndicatorDisplay extends VBox {
    Label text;
    Label graphic;
    ProgressBar bar;
    String name;

    public IndicatorDisplay(String name) {
        this.setAlignment(Pos.CENTER);
        this.name = name;
        this.text = new Label(this.name);
        this.bar = new ProgressBar();
        try {

            Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/" + name + ".png")).toExternalForm());
            this.graphic = new Label();
            graphic.setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
        this.getChildren().add(graphic);
        this.getChildren().add(text);
        this.getChildren().add(bar);
        this.setEffect(new DropShadow());

    }

    public void update(Game game) {
        double newValue = Math.floor(game.getIndicatorValue(Indicator.valueOf(name)) * 100) / 10000;
        this.bar.setProgress(newValue);
        if (newValue < 0.15) {
            bar.setStyle("-fx-accent: red");
        } else if (newValue < 0.3) {
            bar.setStyle("-fx-accent: yellow");
        } else {
            bar.setStyle("-fx-accent: #00A2E8");
        }
    }

}
