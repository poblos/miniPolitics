package jobs;

import events.Condition;

public record AdvisorSkillCondition(Job job, Trait trait) implements Condition {
}

