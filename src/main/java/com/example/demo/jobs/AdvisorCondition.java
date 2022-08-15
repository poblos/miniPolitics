package com.example.demo.jobs;

import com.example.demo.event.Condition;

public class AdvisorCondition implements Condition {
    private Job job;

    public Job getJob() {
        return job;
    }
}
