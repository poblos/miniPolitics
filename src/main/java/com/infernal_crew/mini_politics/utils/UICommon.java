package com.infernal_crew.mini_politics.utils;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UICommon {
    public static void addNamedData(Pane pane, String name, String data) {
        addHighlightedText(pane, name);
        addNormalText(pane,data);
    }

    public static void addHighlightedText(Pane pane, String data) {
        Text text = new Text(data);
        text.getStyleClass().add("highlightedText");
        pane.getChildren().add(text);
    }

    public static void addHighlightedClickableText(Pane pane, String data, EventHandler<? super MouseEvent> handler) {
        Text text = new Text(data);
        text.getStyleClass().add("highlightedText");
        text.setOnMouseClicked(handler);
        pane.getChildren().add(text);
    }

    public static void addNormalText(Pane pane, String data) {
        Text text = new Text(data);
        text.getStyleClass().add("normalText");
        pane.getChildren().add(text);
    }

    public static void breakLine(TextFlow flow) {
        flow.getChildren().add(new Text("\n"));
    }

}
