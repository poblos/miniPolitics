package events_classes;

public class PolicyCondition implements Condition {
    private final int id;
    private final int option;

    public int getId() {
        return id;
    }

    public int getOption() {
        return option;
    }

    public PolicyCondition(int id, int option) {
        this.id = id;
        this.option = option;
    }
}
