package com.infernal_crew.mini_politics.utils;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import javafx.scene.paint.Color;

public class ColorAdapter {

    @ToJson
    public String toJson(Color color) {
        return color.toString();
    }

    @FromJson
    public Color fromJson(String colorString) {
        return Color.web(colorString);
    }
}
