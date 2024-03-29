package com.infernal_crew.mini_politics.jobs;

import java.util.List;
import java.util.Objects;

public class Person {
    private final Job startingJob;
    private final int id;
    final String name;
    final List<Trait> traits;

    public String getName() {
        return name;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public Person(String name, List<Trait> traits, int id, Job startingJob) {
        this.name = name;
        this.traits = traits;
        this.id = id;
        this.startingJob = startingJob;
    }

    public int getId() {
        return id;
    }

    public Job getStartingJob() {
        return startingJob;
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
