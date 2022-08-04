package modifiers;

public class Modifier {
    String name;
    String description;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return description;
    }
}
