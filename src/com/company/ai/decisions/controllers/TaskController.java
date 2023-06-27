package com.company.ai.decisions.controllers;

public class TaskController {
    private boolean done;
    private boolean success;

    public TaskController() {
        this.done = false;
        this.success = false;
    }

    public void finishWithSuccess() {
        this.success = true;
        this.done = true;
    }

    public void finishWithFailure() {
        this.success = false;
        this.done = true;
    }

    public boolean succeeded() {
        return this.success;
    }

    public boolean finished() {
        return this.done;
    }

    public void reset() {
        this.done = false;
    }
}

