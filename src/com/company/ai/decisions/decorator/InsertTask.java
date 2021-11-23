package com.company.ai.decisions.decorator;

import com.company.ai.decisions.Task;
import com.company.entities.human.GameEntity;

public abstract class InsertTask extends TaskDecorator {

    public InsertTask(Task task) {
        super(task);
        addLeaves();
    }

    @Override
    public void doAction(GameEntity entity) {
        task.doAction(entity);
    }

    protected abstract void addLeaves();
}
