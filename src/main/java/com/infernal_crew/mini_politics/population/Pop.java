package com.infernal_crew.mini_politics.population;

import java.util.Map;

public class Pop {
    int count;
    float partySupport;
    Map<Feature, Float> features;

    @Override
    public String toString() {
        return "Pop{" +
                "count=" + count +
                ", partySupport=" + partySupport +
                ", features=" + features +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setPartySupport(float partySupport) {
        this.partySupport = partySupport;
    }

    public float getPartySupport() {
        return partySupport;
    }

    public Map<Feature, Float> getFeatures() {
        return features;
    }
}
