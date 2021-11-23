package com.company.ai.decisions.single_task;

import com.company.ai.decisions.Task;
import com.company.ai.decisions.controller.TaskController;

public abstract class LeafTask implements Task {
    protected TaskController control;

    public LeafTask() {
        this.control = new TaskController(this);
    }

    @Override
    public TaskController getControl() {
        return this.control;
    }
}


