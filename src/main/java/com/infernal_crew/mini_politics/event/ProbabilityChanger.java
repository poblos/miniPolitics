package com.infernal_crew.mini_politics.event;

import java.util.List;

public record ProbabilityChanger(List<Condition> conditions, int probChange) {
}
