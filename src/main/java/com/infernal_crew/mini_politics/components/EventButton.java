package com.infernal_crew.mini_politics.components;

import javafx.scene.control.Button;

public class EventButton extends Button {
    private final int click;

    public int getClick() {
        return click;
    }

    public EventButton(int click) {
        super();
        this.click = click;
    }
}
