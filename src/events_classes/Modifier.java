package events_classes;

public class Modifier {
    String name;
    String description;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "events_classes.Modifier{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
