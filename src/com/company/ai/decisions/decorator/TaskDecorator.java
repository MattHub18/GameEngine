package com.company.ai.decisions.decorator;

import com.company.ai.decisions.Task;
import com.company.ai.decisions.controller.TaskController;

public abstract class TaskDecorator implements Task {
    protected Task task;

    public TaskDecorator(Task task) {
        this.task = task;
        this.task.getControl().setTask(this);
    }

    @Override
    public boolean checkConditions() {
        return task.checkConditions();
    }

    @Override
    public TaskController getControl() {
        return task.getControl();
    }

    @Override
    public void start() {
        task.start();
    }
}
