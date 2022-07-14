package events_classes;

import game.Game;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final String title;
    private final String description;
    private final List<Option> options;
    private final Trigger trigger;

    public Event(String title, String description, List<Option> options) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = new Trigger();
    }

    public Event(String title, String description, List<Option> options, List<Condition> yesConditions, List<Condition> noConditions) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.trigger = new Trigger(yesConditions, noConditions);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Option> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return  title + '\n' + description + '\n' + options;
    }

    public Event adjust(Game game) {
        ArrayList<Option> newOptions= new ArrayList<>();
        for(Option option : options) {
            if (game.meetsConditions(option)) {
                newOptions.add(option);
            }
        }
        return new Event(title, description,newOptions);
    }
}
