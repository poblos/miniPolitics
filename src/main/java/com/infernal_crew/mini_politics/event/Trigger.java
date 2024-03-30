package com.infernal_crew.mini_politics.event;

import com.infernal_crew.mini_politics.game.Game;

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

    public boolean isMet(Game game){
        for (Condition condition : yes) {
            if (!condition.met(game)) {
                return false;
            }
        }
        for (Condition condition : no) {
            if (condition.met(game)) {
                return false;
            }
        }
        return true;
    }

    public List<Condition> getYes() {
        return yes;
    }

    public List<Condition> getNo() {
        return no;
    }
}

