package com.example.demo;

import com.example.demo.game.Game;
import com.example.demo.indicators.Indicator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


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
            Image image = new Image("/" + name + ".png");
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
        this.bar.setProgress(Math.floor(game.getIndicatorValue(Indicator.valueOf(name)) * 100) / 10000);
    }

}
