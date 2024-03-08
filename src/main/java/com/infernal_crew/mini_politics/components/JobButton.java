package com.infernal_crew.mini_politics.components;

import com.infernal_crew.mini_politics.jobs.Job;
import javafx.scene.control.Button;

public class JobButton extends Button {
    private final Job job;

    public JobButton(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }
}