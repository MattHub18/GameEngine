package com.company.ai.decisions.controller;

import com.company.ai.decisions.Task;

public class TaskController {
    private boolean done;
    private boolean success;
    private boolean started;
    private Task task;

    public TaskController(Task task) {
        this.task = task;
        this.done = false;
        this.success = false;
        this.started = false;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void safeStart() {
        this.started = true;
        task.start();
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

    public boolean started() {
        return this.started;
    }

    public void reset() {
        this.done = false;
    }
}
