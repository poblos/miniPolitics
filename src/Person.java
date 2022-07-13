import java.util.List;

public class Person {
    String name;
    List<Trait> traits;

    public String getName() {
        return name;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public Person(String name, List<Trait> traits) {
        this.name = name;
        this.traits = traits;
    }

    @Override
    public String toString() {
        return name + traits;
    }
}
