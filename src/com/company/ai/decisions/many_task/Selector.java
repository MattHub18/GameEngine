package com.company.ai.decisions.many_task;

import com.company.ai.AiInterface;

public class Selector extends ParentTask {

    private int chooseNewTask() {
        if (!control.hasNext())
            return -1;
        control.next();
        return control.getIndex();
    }

    @Override
    public void childFailed() {
        int index = chooseNewTask();
        if (index == -1)
            control.finishWithFailure();
        else
            control.setTask(index);
    }

    @Override
    public void childSucceeded() {
        control.finishWithSuccess();
    }

    @Override
    public double getValue(AiInterface entity) {
        return control.getCurTask().getValue(entity);
    }
}
