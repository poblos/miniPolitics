package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Effect;

public class AdvisorEmployment implements Effect {
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Hires a politician";
    }
}
