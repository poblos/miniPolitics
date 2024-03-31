package com.infernal_crew.mini_politics.jobs;

import com.infernal_crew.mini_politics.indicators.Indicator;

public interface TraitEffect {
    int calculateBonus(Job job, Indicator indicator);
}
