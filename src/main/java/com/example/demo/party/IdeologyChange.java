package com.example.demo.party;

import com.example.demo.event.Effect;

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
