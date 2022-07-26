package gui;

import events_classes.Event;
import events_classes.Option;
import game.Game;

import javax.swing.*;
import java.awt.*;

public class EventDisplay extends JPanel {

    public EventDisplay(Event event, Game game) {
        this.setBounds(320, 180, 640, 360);
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(TOP_ALIGNMENT);
        this.setBackground(Color.BLUE);

        // Title setup
        JLabel title = new JLabel();
        title.setText(event.getTitle());
        title.setBounds(320,180,640,180);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        this.add(title);

        // Desc setup
        JTextArea desc = new JTextArea();
        desc.setText(event.getDescription());
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBounds(320,360,640,180);
        desc.setFont(new Font("Serif", Font.BOLD, 16));
        this.add(desc);

        //Jensons setup
        int i = 0;
        for (Option option : event.getOptions()) {
            EventButton button = new EventButton(i);
            button.setText(option.getDescription());
            button.setBounds(320,450,640,180);
            button.addActionListener(game);
            this.add(button);
            i++;
        }
    }

}
