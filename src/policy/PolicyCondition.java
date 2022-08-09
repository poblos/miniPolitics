package policy;

import events.Condition;

public record PolicyCondition(int id, int option) implements Condition {

}
