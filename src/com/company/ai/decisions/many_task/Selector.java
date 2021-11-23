package com.company.ai.decisions.many_task;

import com.company.ai.decisions.Task;

public class Selector extends ParentTask {

    public Task chooseNewTask() {
        Task task = null;
        boolean found = false;
        int curPos = control.subtasks.indexOf(control.curTask);

        while (!found) {
            if (curPos == (control.subtasks.size() - 1)) {
                task = null;
                break;
            }

            curPos++;

            task = control.subtasks.elementAt(curPos);
            if (task.checkConditions()) {
                found = true;
            }
        }

        return task;
    }

    @Override
    public void childFailed() {
        control.curTask = chooseNewTask();
        if (control.curTask == null) {
            control.finishWithFailure();
        }
    }

    @Override
    public void childSucceeded() {
        control.finishWithSuccess();
    }
}
