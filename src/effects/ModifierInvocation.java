package effects;

public class ModifierInvocation implements Effect{
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " will be added to active modifiers";
    }
}
