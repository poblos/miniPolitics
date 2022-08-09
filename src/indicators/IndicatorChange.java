package indicators;

import events.Effect;

public record IndicatorChange(Indicator indicator, int change) implements Effect {

    @Override
    public String toString() {
        return indicator + ": " + change;
    }
}
