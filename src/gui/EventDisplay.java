package gui;

import events_classes.Event;

import javax.swing.*;
import java.awt.*;

public class EventDisplay extends JPanel {

    public EventDisplay(Event event) {
        JTextArea desc = new JTextArea();
        JLabel title = new JLabel();
        this.setBounds(320, 180, 640, 360);
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setBackground(Color.BLUE);

        title.setText(event.getTitle());
        title.setBounds(320,180,640,180);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        desc.setText(event.getDescription());
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBounds(320,360,640,180);
        desc.setFont(new Font("Serif", Font.BOLD, 16));

        this.add(title);
        this.add(desc);

    }

}
