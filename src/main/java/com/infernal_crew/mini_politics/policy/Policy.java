package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.population.Feature;

import java.util.Arrays;

public class Policy {
    private final int id;
    private int currentOption;
    private int weight;
    private final String name;
    private final PolicyOption[] options;
    private final Feature feature;

    public Policy(int id, String name, Feature feature, PolicyOption[] options, int weight) {
        this.id = id;
        this.name = name;
        this.options = options;
        this.feature = feature;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public PolicyOption[] getOptions() {
        return options;
    }

    public int getId() {
        return id;
    }

    public int getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(int currentOption) {
        this.currentOption = currentOption;
    }

    public Feature getFeature() {
        return feature;
    }

    public int getTotalOptions() {
        return options.length;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", currentOption=" + currentOption +
                ", name='" + name + '\'' +
                ", options=" + Arrays.toString(options) +
                '}';
    }
}
