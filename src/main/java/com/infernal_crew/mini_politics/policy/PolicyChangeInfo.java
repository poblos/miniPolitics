package com.infernal_crew.mini_politics.policy;

import com.infernal_crew.mini_politics.population.Feature;

public record PolicyChangeInfo(Feature feature,
                               float start,
                               float end,
                               float weight) {
}
