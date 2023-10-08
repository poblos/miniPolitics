package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;

public class AdvisorCondition implements Condition {
    private Job job;

    public Job getJob() {
        return job;
    }
}
