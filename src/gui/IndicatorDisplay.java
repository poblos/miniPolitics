package gui;

import javax.swing.*;
import java.io.File;

import game.*;
import indicators.Indicator;

public class IndicatorDisplay extends JLabel{

    ImageIcon ikona;
    String name;
    Indicator ind;

    public IndicatorDisplay(Indicator name) {
        this.name = name.toString();
        this.ind = name;
        this.setText(this.name + ": " + 0f);
        this.setHorizontalTextPosition(CENTER);
        this.setVerticalTextPosition(BOTTOM);
        File file = new File("./src/gui/" + this.name + ".png");
        if(file.exists()) {
            ikona = new ImageIcon("./src/gui/" + this.name + ".png");
        } else {
            ikona = new ImageIcon("./src/gui/NotFound.png");
        }
        this.setIcon(ikona);
    }

    public void update(Game game) {
        this.setText(this.name + ": " + Math.floor(game.getIndicatorValue(ind)*100)/100);
    }
}
