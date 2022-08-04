package media;

import events.Effect;

public class MediaTakeover implements Effect {
    @Override
    public String toString() {
        return "Takes over a random resources.media group of affiliation other than Government";
    }
}
