package jobs;

import events.Condition;

public class AdvisorCondition implements Condition {
    private Job job;

    public Job getJob() {
        return job;
    }
}
