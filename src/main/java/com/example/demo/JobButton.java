package com.example.demo;

import com.example.demo.jobs.Job;
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