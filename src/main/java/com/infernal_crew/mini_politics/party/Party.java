package com.infernal_crew.mini_politics.party;

import com.infernal_crew.mini_politics.utils.Identifiable;
import javafx.scene.paint.Color;
import java.util.List;

public class Party implements Comparable<Party>, Identifiable {
    private final String id;
    private final String name;
    private final String description;
    private final List<Ideology> ideologies;
    private final Color color;
    private final int righteousness;
    private int seats;

    public Party(String id, String name, String description, List<Ideology> ideologies, Color color, int righteousness, int seats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ideologies = ideologies;
        this.color = color;
        this.righteousness = righteousness;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public List<Ideology> ideologies() {
        return ideologies;
    }

    public Color color() {
        return color;
    }

    public int righteousness() {
        return righteousness;
    }

    public int seats() {
        return seats;
    }

    public void setSeats(int newSeats) {
        seats = newSeats;
    }

    @Override
    public int compareTo(Party other) {
        return Integer.compare(this.righteousness, other.righteousness);
    }

    @Override
    public String toString() {
        return "Party{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ideologies=" + ideologies +
                ", color=" + color +
                ", righteousness=" + righteousness +
                ", seats=" + seats +
                '}';
    }
}
