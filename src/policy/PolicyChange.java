package policy;

import events.Effect;

public record PolicyChange(int id, int option) implements Effect {
}
