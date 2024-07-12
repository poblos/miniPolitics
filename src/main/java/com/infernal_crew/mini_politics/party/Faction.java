package com.infernal_crew.mini_politics.party;

public class Faction {
    private String id;

    private String name;
    private float loyalty;
    private int members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(float loyalty) {
        this.loyalty = loyalty;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
