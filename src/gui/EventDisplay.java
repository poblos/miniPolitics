package gui;

import events_classes.Event;

import javax.swing.*;
import java.awt.*;

public class EventDisplay extends JPanel {

    Event wyd;
    String wrapped;
    JTextArea desc = new JTextArea();
    JLabel title = new JLabel();

    public EventDisplay(Event event, GUI gui) {
        this.wyd = event;
        wrapped = wrapText(event.getDescription());
        this.setBounds(0, 360, 640, 360);
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.setAlignmentY(BOTTOM_ALIGNMENT);
        this.setBackground(Color.BLUE);

        title.setText(event.getTitle());
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setAlignmentY(TOP_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 28));

        desc.setText(this.wrapped);
        desc.setAlignmentX(LEFT_ALIGNMENT);
        desc.setFont(new Font("Serif", Font.BOLD, 16));

        this.add(title);
        this.add(desc);

        gui.add(this);
        this.setVisible(true);
    }

    private String wrapText(String text) {
        char[] litery;
        int count = 0;
        litery = text.toCharArray();
        for(int i = 0; i < litery.length; i++) {
            if(count >= 36) {
                while(litery[i]!=' ') {
                    i--;
                }
                count = 0;
                litery[i] = '\n';
            }
            count++;
        }
        return String.valueOf(litery);
    }
}
