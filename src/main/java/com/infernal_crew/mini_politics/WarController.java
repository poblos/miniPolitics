package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class WarController extends BarController {
    @FXML private ListView<WarEvent> listView;
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
    public void initialize() {
        //Placeholder
        ObservableList<WarEvent> events = FXCollections.observableArrayList();

        events.add(new WarEvent("Battle of Bunker Hill", "+3"));
        events.add(new WarEvent("Siege of Yorktown", "+5"));
        events.add(new WarEvent("Winter at Valley Forge", "-2"));

        listView.setItems(events);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<WarEvent> call(ListView<WarEvent> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(WarEvent item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox();
                            hBox.setSpacing(10);
                            Label eventName = new Label(item.getName());
                            eventName.setTextFill(Color.WHITE);

                            Label eventEffect = new Label(item.getEffect());
                            if (item.getEffect().startsWith("+")) {
                                eventEffect.setTextFill(Color.GREEN);
                            } else {
                                eventEffect.setTextFill(Color.RED);
                            }

                            hBox.getChildren().addAll(eventName, eventEffect);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }
}
