package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.Objects;


public class IndicatorDisplay extends VBox {
    Label graphic;
    ProgressBar bar;
    String name;
    Tooltip tooltip;

    public IndicatorDisplay(String name) {
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.name = name;
        this.bar = new ProgressBar();
        try {

            Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/menu_icons/" + name + ".png")).toExternalForm());
            this.graphic = new Label();
            graphic.setGraphic(new ImageView(image));
        } catch (Exception e) {
            System.out.println("problem");
        }
        this.getChildren().add(graphic);
        this.getChildren().add(bar);
        this.setEffect(new DropShadow());

        tooltip = new Tooltip();
        Text firstPart = new Text("Party Cohesion\n");
        firstPart.setFill(Color.YELLOW);
        Text secondPart = new Text("represents the general sentiment in our party; the actions we take against politicians of our own party lower it (for example if we want to tackle corruption or make decisions that are against views of some of our party members, or favor people that aren't in our party for long time); on the contrary, the actions such as covering up for our MPs or listening to the old wing of our party increases it.");
        secondPart.setFill(Color.WHITE);
        TextFlow textFlow = new TextFlow(firstPart, secondPart);

        tooltip.setGraphic(textFlow);

        tooltip.setWrapText(true);
        tooltip.setMaxWidth(200);

        Tooltip.install(this.graphic, tooltip);
        tooltip.setShowDelay(Duration.millis(500));


    }

    public void update(Game game) {
        double newValue = Math.floor(game.getIndicatorValue(Indicator.valueOf(name)) * 100) / 10000;
        this.bar.setProgress(newValue);
        String progressBarClass = "progress-bar-high"; // default to high

        if (newValue < 0.15) {
            progressBarClass = "progress-bar-low";
        } else if (newValue < 0.3) {
            progressBarClass = "progress-bar-medium";
        }

        // Remove all possible classes to avoid style conflicts
        this.bar.getStyleClass().removeAll("progress-bar-low", "progress-bar-medium", "progress-bar-high");
        // Add the appropriate class based on the new value
        this.bar.getStyleClass().add(progressBarClass);
    }



}
