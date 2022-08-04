package jobs;
import events.Effect;

public class AdvisorDismissal implements Effect {
    Job job;
    @Override
    public String toString() {
        return "Fires a politician occupying the mentioned position";
    }

    public Job getJob() {
        return job;
    }
}
