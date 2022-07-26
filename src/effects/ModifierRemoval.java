package effects;

public class ModifierRemoval implements Effect{
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " will be removed from active modifiers";
    }
}
