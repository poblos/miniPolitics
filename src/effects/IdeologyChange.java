package effects;

import party.Ideology;

public class IdeologyChange implements Effect{
    Ideology removed;
    Ideology added;

    public Ideology getRemoved() {
        return removed;
    }

    public Ideology getAdded() {
        return added;
    }
}
