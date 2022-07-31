package policy;

public class Policy {
    private final int id;
    private int currentOption;
    private final String name;
    private final PolicyOption[] options;

    public Policy(int id, String name, PolicyOption[] options) {
        this.id = id;
        this.name = name;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public PolicyOption[] getOptions() {
        return options;
    }

    public int getId() {
        return id;
    }

    public int getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(int currentOption) {
        this.currentOption = currentOption;
    }
}
