package events_classes;

import java.util.List;

public record ProbabilityChanger(List<Condition> conditions, int probChange) {
}
