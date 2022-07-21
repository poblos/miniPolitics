package gui;

import indicators.Indicator;
import game.Game;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    IndicatorDisplay p_cohesion = new IndicatorDisplay(Indicator.PartyCohesion);
    IndicatorDisplay p_support = new IndicatorDisplay(Indicator.PartySupport);
    IndicatorDisplay s_stability = new IndicatorDisplay(Indicator.StateStability);

    JPanel indicators = new JPanel();

    Game game;

    public GUI(Game gra){

        this.game = gra;

        //Frame settings
        this.setSize(1280, 720);
        this.setTitle("miniPolitics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        //Panel settings
        indicators.setBackground(Color.GRAY);
        indicators.setBounds(0, 0, 400, 160);

        indicators.add(p_cohesion);
        indicators.add(p_support);
        indicators.add(s_stability);

        this.add(indicators);
        this.setVisible(true);
        this.updateStats();
    }

    public void updateStats() {
        p_cohesion.update(this.game);
        p_support.update(this.game);
        s_stability.update(this.game);
    }
}
