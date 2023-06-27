package com.company.ai.decisions.decorator;

import com.company.ai.AiInterface;
import com.company.ai.decisions.Task;
import com.company.ai.decisions.controllers.TaskController;

public abstract class TaskDecorator implements Task {
    protected Task task;

    public TaskDecorator(Task task) {
        this.task = task;
    }

    @Override
    public boolean checkConditions(AiInterface entity) {
        return task.checkConditions(entity);
    }

    @Override
    public TaskController getController() {
        return task.getController();
    }

    @Override
    public void start(AiInterface entity) {
        task.start(entity);
    }

    @Override
    public double getValue(AiInterface entity) {
        return task.getValue(entity);
    }

    @Override
    public void reset() {
        task.reset();
    }
}

