package com.example.demo.jobs;

import com.example.demo.event.Effect;

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
