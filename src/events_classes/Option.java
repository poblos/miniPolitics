package events_classes;

import effects.Effect;

import java.util.List;

public class Option {
    private final String description;
    private final List<Effect> effects;
    private final Trigger trigger;
    public Option(String description, List<Effect> effects) {
        this.description = description;
        this.effects = effects;
        this.trigger = new Trigger();
    }

    public Option(String description, List<Effect> effects, Trigger trigger) {
        this.description = description;
        this.effects = effects;
        this.trigger = trigger;
    }

    public String getDescription() {
        return description;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public String toString() {
        if (effects.size() != 0) {
            return '\n' + description + '\n' + effects;
        }
        else {
            return '\n' + description + '\n' + "No effects\n";
        }

    }
}
