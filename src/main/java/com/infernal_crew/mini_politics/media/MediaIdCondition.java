package com.infernal_crew.mini_politics.media;
import com.infernal_crew.mini_politics.event.Condition;
import com.infernal_crew.mini_politics.game.Game;

public class MediaIdCondition implements Condition {
    private Affiliation affiliation;
    private int id;
    public Affiliation getAffiliation() {
        return affiliation;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean met(Game game) {
        for (MediaGroup group : game.getMediaGroups()) {
            if (group.getId() == id) {
                return group.getAffiliation() == affiliation;
            }
        }
        System.out.println("No such media group exists");
        return false;
    }
}

