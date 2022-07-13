public class IndicatorChange implements Effect{
    private final Indicator indicator;
    private final int change;
    public IndicatorChange(Indicator indicator, int change) {
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
