package com.infernal_crew.mini_politics;

public class WarEvent {
    private final String name;
    private final String effect; // Example: "+5", "-5"

    public WarEvent(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }
}
