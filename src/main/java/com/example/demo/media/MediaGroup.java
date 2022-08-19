package com.example.demo.media;

public class MediaGroup {
    String name;
    MediaType type;
    Affiliation affiliation;

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public String getName() {
        return name;
    }

    public MediaType getType() {
        return type;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public String toString() {
        return name + " " + type + " " + affiliation;
    }
}

