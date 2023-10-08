package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.event.Condition;

public record AdvisorSkillCondition(Job job, Trait trait) implements Condition {
}

