package com.infernal_crew.mini_politics.event;

import java.util.ArrayList;
import java.util.List;

public class Trigger {
    final List<Condition> yes;
    final List<Condition> no;

    public Trigger(List<Condition> yes, List<Condition> no) {
        this.yes = yes;
        this.no = no;
    }

    public Trigger() {
        this.yes = new ArrayList<>();
        this.no = new ArrayList<>();
    }

    public List<Condition> getYes() {
        return yes;
    }

    public List<Condition> getNo() {
        return no;
    }
}

