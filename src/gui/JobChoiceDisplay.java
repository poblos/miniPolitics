package gui;

import game.Game;
import jobs.Job;
import jobs.Person;

import javax.swing.*;
import java.awt.*;

public class JobChoiceDisplay extends JPanel {
    public JobChoiceDisplay(Person person, Game game) {
        this.setBounds(320, 180, 640, 360);
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(TOP_ALIGNMENT);

        // Title setup
        JLabel title = new JLabel();
        title.setText("Choose job");
        title.setBounds(320,180,640,180);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        this.add(title);

        // Desc setup
        JTextArea desc = new JTextArea();
        desc.setText("Choose job for:" + person);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setBounds(320,360,640,180);
        desc.setFont(new Font("Serif", Font.BOLD, 16));
        this.add(desc);

        //Jensons setup
        int i = 0;
        for (Job job : Job.values()) {
            JobButton button = new JobButton(i, person);
            button.setText(String.valueOf(job));
            button.setBounds(320,450,640,180);
            button.addActionListener(game);
            this.add(button);
            i++;
        }
    }
}
