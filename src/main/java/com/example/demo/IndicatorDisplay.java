package com.example.demo;

import com.example.demo.game.Game;
import com.example.demo.indicators.Indicator;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class IndicatorDisplay extends Label {

    Image image;
    String name;

    public IndicatorDisplay(String name) {
        this.name = name;
        this.setText(this.name + ": " + 0f);
        try {
            image = new Image("/" + name + ".png");
            setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
    }

    public void update(Game game) {
        this.setText(this.name + ": " + Math.floor(game.getIndicatorValue(Indicator.valueOf(name))*100)/100);
    }

}
