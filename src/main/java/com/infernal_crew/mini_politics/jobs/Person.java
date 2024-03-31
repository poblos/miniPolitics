package com.infernal_crew.mini_politics.jobs;

import java.util.List;
import java.util.Objects;

public class Person {
    private final Job startingJob;
    private final int id;
    final String name;
    final List<String> traits;

    public String getName() {
        return name;
    }

    public List<String> getTraits() {
        return traits;
    }

    public Person(String name, List<String> traits, int id, Job startingJob) {
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

    public boolean hasTrait(String name) {
        for (String trait : traits) {
            if (Objects.equals(trait, name)) {
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
