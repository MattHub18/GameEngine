package com.company.ai.decisions.many_task;


import com.company.ai.decisions.Task;
import com.company.ai.decisions.controller.ParentTaskController;
import com.company.ai.decisions.controller.TaskController;
import com.company.entities.human.GameEntity;

public abstract class ParentTask implements Task {
    ParentTaskController control;

    public ParentTask() {
        this.control = new ParentTaskController(this);
    }

    @Override
    public TaskController getControl() {
        return control;
    }

    @Override
    public boolean checkConditions() {
        return control.subtasks.size() > 0;
    }

    public abstract void childSucceeded();

    public abstract void childFailed();

    @Override
    public void doAction(GameEntity entity) {
        while (!control.finished()) {
            if (!control.curTask.getControl().started()) {
                control.curTask.getControl().safeStart();
            }
            control.curTask.doAction(entity);
            if (control.curTask.getControl().succeeded()) {
                childSucceeded();
            } else {
                childFailed();
            }
        }
    }

    @Override
    public void start() {
        control.curTask = control.subtasks.firstElement();
        if (control.curTask == null) {
            throw new NullPointerException("Current task has a null action");
        }
    }
}
