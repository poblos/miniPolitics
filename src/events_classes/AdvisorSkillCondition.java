package events_classes;

import jobs.Job;
import jobs.Trait;

public record AdvisorSkillCondition(Job job, Trait trait) implements Condition {
}

