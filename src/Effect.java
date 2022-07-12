public class Effect {
    private Indicator indicator;
    private int change;

    public Effect(Indicator indicator, int change) {
        this.indicator = indicator;
        this.change = change;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public int getChange() {
        return change;
    }

    @Override
    public String toString() {
        return  indicator + ": " + change;
    }
}
