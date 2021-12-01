package com.company.ai.decisions.many_task;

import com.company.ai.decisions.Task;
import com.company.ai.decisions.UtilityBaseSystem;

public class Chooser extends ParentTask {

    public Task chooseNewTask() {
        Task task = null;
        boolean found = false;
        int visited = 0;
        int curPos = UtilityBaseSystem.getPositionByPoints(control.subtasks);

        while (!found) {
            if (visited == control.subtasks.size()) {
                task = null;
                break;
            }

            task = control.subtasks.elementAt(curPos);
            if (task.checkConditions()) {
                found = true;
            }

            visited++;
            curPos = (curPos + 1) % control.subtasks.size();
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

    @Override
    public void start() {
        control.curTask = chooseNewTask();
        if (control.curTask == null) {
            throw new NullPointerException("Current task has a null action");
        }
    }
}
