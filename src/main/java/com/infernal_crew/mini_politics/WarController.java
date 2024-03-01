package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class WarController extends BarController {
    @FXML
    ProgressBar bar;
    @Override
    public void update(Game game) {
        double newValue = Math.floor(game.getIndicatorValue(Indicator.valueOf("NarongWarBalance")) * 100) / 10000;
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
