package com.infernal_crew.mini_politics.modifiers;

import com.infernal_crew.mini_politics.event.Effect;

public class ModifierRemoval implements Effect {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " will be removed from active com.example.demo.resources.com.example.demo.modifiers";
    }
}
