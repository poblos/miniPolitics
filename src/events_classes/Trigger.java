package events_classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trigger {
    List<Condition> yes;
    List<Condition> no;

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

