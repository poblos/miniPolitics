package com.example.demo.jobs;

import com.example.demo.event.Effect;

public class RandomAdvisorDismissal implements Effect {
    @Override
    public String toString() {
        return "Dismisses a politician from the random position";
    }
}
