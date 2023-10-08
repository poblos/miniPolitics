package com.infernal_crew.mini_politics.modifiers;

import com.infernal_crew.mini_politics.event.Effect;

public class ModifierInvocation implements Effect {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " will be added to active com.example.demo.resources.com.example.demo.modifiers";
    }
}
