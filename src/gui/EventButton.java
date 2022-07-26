package gui;

import javax.swing.*;

public class EventButton extends JButton {
    private final int id;

    public EventButton(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
