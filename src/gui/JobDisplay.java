package gui;


import game.Game;
import jobs.Job;

import javax.swing.*;
import java.io.File;

public class JobDisplay extends JLabel {
    ImageIcon ikona;
    String name;
    Job job;

    public JobDisplay(Job job) {
        this.name = job.toString();
        this.job = job;
        this.setText(this.name + ": " + "none");
        this.setHorizontalTextPosition(CENTER);
        this.setVerticalTextPosition(BOTTOM);
        File file = new File("./src/gui/people_images/" + this.name + ".png");
        if (file.exists()) {
            ikona = new ImageIcon("./src/gui/people_images/" + this.name + ".png");
        } else {
            ikona = new ImageIcon("./src/gui/people_images/ModelNotFound.png");
        }
        this.setIcon(ikona);
    }

    public void update(Game game) {
        if (game.getEmployed(job) == null) {
            this.setText(this.name + ": " + "none");
            ikona = new ImageIcon("./src/gui/people_images/ModelNotFound.png");
            this.setIcon(ikona);
            return;
        }
        this.setText(this.name + ": " + game.getEmployed(job).getName());
        this.setIcon(new ImageIcon("./src/gui/people_images/" + game.getEmployed(job).getName() + ".png"));
    }
}

