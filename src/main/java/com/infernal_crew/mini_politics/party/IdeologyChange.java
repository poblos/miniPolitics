package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.event.Effect;

public class IdeologyChange implements Effect {
    Ideology removed;
    Ideology added;

    public Ideology getRemoved() {
        return removed;
    }

    public Ideology getAdded() {
        return added;
    }
}
