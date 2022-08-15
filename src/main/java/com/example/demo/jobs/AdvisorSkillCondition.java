package com.example.demo.jobs;

import com.example.demo.event.*;

public record AdvisorSkillCondition(Job job, Trait trait) implements Condition {
}

