import effects.Effect;

import java.util.List;

public class Option {
    String description;
    List<Effect> effects;
    public Option(String description, List<Effect> effects) {
        this.description = description;
        this.effects = effects;
    }

    public String getDescription() {
        return description;
    }

    public List<Effect> getEffects() {
        return effects;
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
