package events_classes;

import jobs.Job;
import jobs.Trait;

public class AdvisorSkillCondition implements Condition {
    private final Job job;

    private final Trait trait;

    public AdvisorSkillCondition(Job job, Trait trait) {
        this.job = job;
        this.trait = trait;
    }

    public Job getJob() {
        return job;
    }

    public Trait getTrait() {
        return trait;
    }
}

