package effects;

public class PolicyChange implements Effect{
    private final int id;
    private final int option;

    public PolicyChange(int id, int option) {
        this.id = id;
        this.option = option;
    }

    public int getId() {
        return id;
    }

    public int getOption() {
        return option;
    }
}
