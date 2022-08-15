package com.example.demo.jobs;

import java.util.List;
import java.util.Objects;

public class Person {
    private final int id;
    final String name;
    final List<Trait> traits;

    public String getName() {
        return name;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public Person(String name, List<Trait> traits, int id) {
        this.name = name;
        this.traits = traits;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean hasTrait(Trait trait) {
        for (Trait trait2 : traits) {
            if (Objects.equals(trait, trait2)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name + traits;
    }


}
