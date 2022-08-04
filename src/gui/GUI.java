package gui;

import events.Event;
import game.Game;
import indicators.Indicator;
import jobs.Person;

import javax.swing.*;
import java.awt.*;

import static jobs.Job.*;

public class GUI extends JFrame {

    public JPanel currentDisplay;
    IndicatorDisplay p_cohesion = new IndicatorDisplay(Indicator.PartyCohesion);
    IndicatorDisplay p_support = new IndicatorDisplay(Indicator.PartySupport);
    IndicatorDisplay s_stability = new IndicatorDisplay(Indicator.StateStability);

    JobDisplay whip = new JobDisplay(Whip);

    JobDisplay propagandist = new JobDisplay(Propagandist);

    JobDisplay strategist = new JobDisplay(Strategist);

    private final Game game;

    public GUI(Game gra) {

        this.game = gra;

        //Frame settings
        this.setLayout(null);
        this.setSize(1280, 720);
        this.setTitle("miniPolitics");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/gui/icon.png");
        this.setIconImage(icon);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //Adding components
        this.add(createIndicators());

        try {
            this.add(currentDisplay);
        } catch (Exception ignored) {

        }
        this.add(createBackground());
        this.setVisible(true);
        this.updateStats();
    }

    public void updateStats() {
        p_cohesion.update(this.game);
        p_support.update(this.game);
        s_stability.update(this.game);
        whip.update(this.game);
        propagandist.update(this.game);
        strategist.update(this.game);
    }

    private JPanel createBackground() {
        JPanel background = new JPanel();
        background.setBounds(0, 0, 1280, 720);
        background.setLayout(null);

        JLabel bgLabel = new JLabel();
        bgLabel.setBounds(0, 0, 1280, 720);
        ImageIcon bgIcon = new ImageIcon("src/gui/background.png");
        bgLabel.setIcon(bgIcon);
        background.add(bgLabel);
        background.setOpaque(false);
        return background;
    }

    private JPanel createIndicators() {
        JPanel indicators = new JPanel();
        indicators.setBackground(Color.LIGHT_GRAY);

        indicators.setBounds(0, 0, 1280, 160);

        indicators.add(p_cohesion);
        indicators.add(p_support);
        indicators.add(s_stability);
        indicators.add(whip);
        indicators.add(propagandist);
        indicators.add(strategist);
        return indicators;
    }


    public void newEvent(Event event, Game game) {
        try {
            remove(currentDisplay);
        } catch (Exception ignored) {

        }
        this.currentDisplay = new EventDisplay(event, game);
        add(currentDisplay);
        setVisible(true);
    }

    public void jobWindow(Person person) {
        remove(currentDisplay);
        this.currentDisplay = new JobChoiceDisplay(person, game);
        add(currentDisplay);
        setVisible(true);
    }
}
