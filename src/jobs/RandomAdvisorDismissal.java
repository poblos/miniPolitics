package jobs;

import events.Effect;

public class RandomAdvisorDismissal implements Effect {
    @Override
    public String toString() {
        return "Dismisses a politician from the random position";
    }
}
