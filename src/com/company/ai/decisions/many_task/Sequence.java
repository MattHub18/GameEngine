package com.company.ai.decisions.many_task;

import com.company.ai.AiInterface;
import com.company.ai.decisions.Task;

public class Sequence extends ParentTask {

    @Override
    public void childFailed() {
        control.finishWithFailure();
    }

    @Override
    public void childSucceeded() {
        if (control.hasNext())
            control.next();
        else
            control.finishWithSuccess();
    }

    @Override
    public double getValue(AiInterface entity) {
        int sum = 0;
        for (Task t : control.getSubtasks())
            sum += t.getValue(entity);
        return sum;
    }
}
