package com.company.ai.decisions.many_task;

import com.company.ai.AiInterface;
import com.company.ai.decisions.Task;
import com.company.ai.decisions.controllers.ParentTaskController;
import com.company.ai.decisions.controllers.TaskController;
import com.company.entities.entity.GameEntity;

public abstract class ParentTask implements Task {
    protected final ParentTaskController control;

    public ParentTask() {
        this.control = new ParentTaskController();
    }

    @Override
    public boolean checkConditions(AiInterface entity) {
        return control.getSize() > 0;
    }

    public abstract void childSucceeded();

    public abstract void childFailed();

    @Override
    public void doAction(GameEntity entity) {
        while (!control.finished()) {
            if (control.getCurTask().checkConditions((AiInterface) entity)) {
                control.getCurTask().start((AiInterface) entity);
                control.getCurTask().doAction(entity);
                if (control.getCurTask().getController().succeeded())
                    childSucceeded();
                else
                    childFailed();
            } else
                childFailed();
        }
    }

    @Override
    public TaskController getController() {
        return control;
    }

    @Override
    public void start(AiInterface entity) {
        control.start();
    }

    @Override
    public void reset() {
        for (Task t : control.getSubtasks()) {
            t.reset();
        }
        control.reset();
    }
}
