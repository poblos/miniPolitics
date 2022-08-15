package com.example.demo.event;

import java.util.List;

public record ProbabilityChanger(List<Condition> conditions, int probChange) {
}
