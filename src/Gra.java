import javax.swing.*;
import java.util.*;

public class Gra {
    Map<Indicator,Integer> values;
    int round;
    List<Event> events;
    private final transient Random random;
    private final transient Scanner scanner;
    public Gra (ArrayList<Event> events) {
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion,75);
        values.put(Indicator.StateStability,75);
        values.put(Indicator.PartySupport,75);
        this.round = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.events = events;
    }
    public void symuluj(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("\nCurrent stats:\n" + values.toString() + "\n");
            Event currentEvent = events.get(random.nextInt(events.size()));
            System.out.println(currentEvent.toString());
            int option = scanner.nextInt();
            try {
                chooseOption(currentEvent, option);
            }
            catch(Exception e) {
                chooseOption(currentEvent, 0);
            }
            round++;
        }
    }

    private void chooseOption(Event currentEvent, int option) {
        List<Effect> effects = currentEvent.getOptions().get(option).getEffects();
        for(Effect effect : effects) {
            values.put(effect.getIndicator(),effect.getChange()+values.get(effect.getIndicator()));
        }
    }
}
