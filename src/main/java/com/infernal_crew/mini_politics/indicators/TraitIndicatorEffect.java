package com.infernal_crew.mini_politics.indicators;

import com.infernal_crew.mini_politics.jobs.Job;
import com.infernal_crew.mini_politics.jobs.TraitEffect;

public class TraitIndicatorEffect implements TraitEffect {
    private final Indicator indicator;
    private final int percent;
    private final Job job;

    public TraitIndicatorEffect(Indicator indicator, Job job, int percent) {
        this.indicator = indicator;
        this.percent = percent;
        this.job = job;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public int getPercent() {
        return percent;
    }

    public Job getJob() {
        return job;
    }

    @Override
    public int calculateBonus(Job job, Indicator indicator) {
        if (job == this.job && indicator == this.indicator) {
            return percent;
        } else {
            return 0;
        }
    }
}
