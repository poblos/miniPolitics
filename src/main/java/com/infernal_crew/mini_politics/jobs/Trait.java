package com.infernal_crew.mini_politics.jobs;

import java.util.List;
import java.util.Objects;

public class Trait {
    private String name;
    private String description;
    private List<TraitEffect> effects;

    public Trait(String name, String description, List<TraitEffect> effects) {
        this.name = name;
        this.description = description;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<TraitEffect> getEffects() {
        return effects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trait trait = (Trait) o;
        return Objects.equals(name, trait.name);
    }
}