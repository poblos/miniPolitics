package gui;

import events_classes.Event;
import indicators.Indicator;
import game.Game;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    public EventDisplay eventDisplay;
    IndicatorDisplay p_cohesion = new IndicatorDisplay(Indicator.PartyCohesion);
    IndicatorDisplay p_support = new IndicatorDisplay(Indicator.PartySupport);
    IndicatorDisplay s_stability = new IndicatorDisplay(Indicator.StateStability);

    private final Game game;

    public GUI(Game gra){

        this.game = gra;

        //Frame settings
        this.setLayout(null);
        this.setSize(1280, 720);
        this.setTitle("miniPolitics");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //Adding componenents
        this.add(createIndicators());
        this.add(createBackground());
        try {
            this.add(eventDisplay);
        }
        catch (Exception ignored) {

        }
        this.setVisible(true);
        this.updateStats();
    }

    public void updateStats() {
        p_cohesion.update(this.game);
        p_support.update(this.game);
        s_stability.update(this.game);
    }

    private JPanel createBackground() {
        JPanel background = new JPanel();
        background.setBounds(0,0,1280,720);
        background.setBackground(Color.red);
        background.setLayout(null);

        JLabel bgLabel = new JLabel();
        bgLabel.setBounds(0,0,1280, 720);
        ImageIcon bgIcon = new ImageIcon("src/gui/background.png");
        bgLabel.setIcon(bgIcon);
        background.add(bgLabel);
        background.setOpaque(false);
        return background;
    }

    private JPanel createIndicators() {
        JPanel indicators = new JPanel();
        indicators.setBackground(Color.GRAY);
        indicators.setBounds(0, 0, 400, 160);

        indicators.add(p_cohesion);
        indicators.add(p_support);
        indicators.add(s_stability);
        indicators.setOpaque(true);
        return indicators;
    }


    public void newEvent(Event event, Game game) {
        try {
            remove(eventDisplay);
        }
         catch (Exception ignored) {

         }
        this.eventDisplay = new EventDisplay(event,game);
        add(eventDisplay);
        setVisible(true);
    }
}
