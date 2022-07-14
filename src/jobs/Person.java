package jobs;

import jobs.Trait;

import java.util.List;
import java.util.Objects;

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

    public boolean hasTrait(Trait trait) {
        for (Trait trait2 : traits) {
            if (Objects.equals(trait, trait2)) {
                return true;
            }
        }
        return false;
    }
}
