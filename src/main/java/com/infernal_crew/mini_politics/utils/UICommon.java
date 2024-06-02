package com.infernal_crew.mini_politics.utils;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UICommon {
    public static void addNamedData(TextFlow flow, String name, String data) {
        Text text = new Text(name);
        text.getStyleClass().add("highlightedText");
        flow.getChildren().add(text);
        text = new Text(data);
        text.getStyleClass().add("normalText");
        flow.getChildren().add(text);
    }
}
