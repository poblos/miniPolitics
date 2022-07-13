import javax.swing.*;
import java.util.*;

public class Gra {
    Map<Indicator,Integer> values;
    Map<Job, Person> employed;
    int round;
    List<Event> events;
    List<Person> people;
    private final transient Random random;
    private final transient Scanner scanner;

    public Gra (ArrayList<Event> events, ArrayList<Person> people) {
        values = new HashMap<>();
        values.put(Indicator.PartyCohesion,75);
        values.put(Indicator.StateStability,75);
        values.put(Indicator.PartySupport,75);
        this.round = 0;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.employed = new HashMap<>();
        this.events = events;
        this.people = people;
    }
    public void symuluj(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            System.out.println("\nCurrent stats:\n" + values.toString() + "\n");
            System.out.println(employed.toString());
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
            if (effect.getClass() == IndicatorChange.class) {
                values.put(((IndicatorChange)effect).getIndicator(),((IndicatorChange)effect).getChange()+values.get(((IndicatorChange)effect).getIndicator()));
            }
            if (effect.getClass() == AdvisorEmployment.class) {
                Person newPerson = people.get(random.nextInt(people.size()));
                System.out.println(newPerson.toString());
                employed.put(Job.Propagandist, newPerson);
            }
        }
    }
}
