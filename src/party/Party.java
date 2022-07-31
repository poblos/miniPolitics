package party;

import java.util.List;

public class Party {
    private final String name;
    private final String description;
    private final List<Ideology> ideologies;

    public Party(String name, String description, List<Ideology> ideologies) {
        this.name = name;
        this.description = description;
        this.ideologies = ideologies;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Ideology> getIdeologies() {
        return ideologies;
    }
}
