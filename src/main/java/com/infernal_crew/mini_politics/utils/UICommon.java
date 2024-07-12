package com.infernal_crew.mini_politics.utils;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UICommon {
    public static void addNamedData(TextFlow flow, String name, String data) {
        addHighlightedText(flow, name);
        addNormalText(flow,data);
    }

    public static void addHighlightedText(TextFlow flow, String data) {
        Text text = new Text(data);
        text.getStyleClass().add("highlightedText");
        flow.getChildren().add(text);
    }

    public static void addNormalText(TextFlow flow, String data) {
        Text text = new Text(data);
        text.getStyleClass().add("normalText");
        flow.getChildren().add(text);
    }

    public static void breakLine(TextFlow flow) {
        flow.getChildren().add(new Text("\n"));
    }
}
