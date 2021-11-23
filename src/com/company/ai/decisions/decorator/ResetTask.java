package com.company.ai.decisions.decorator;

import com.company.ai.decisions.Task;
import com.company.entities.human.GameEntity;

public class ResetTask extends TaskDecorator {

    public ResetTask(Task task) {
        super(task);
    }

    @Override
    public void doAction(GameEntity entity) {
        task.doAction(entity);
        if (task.getControl().finished()) {
            task.getControl().reset();
        }
    }
}
