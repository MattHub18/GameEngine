package com.company.ai.decisions.many_task;

public class Sequence extends ParentTask {

    @Override
    public void childFailed() {
        control.finishWithFailure();
    }

    @Override
    public void childSucceeded() {
        int curPos = control.subtasks.indexOf(control.curTask);
        if (curPos == (control.subtasks.size() - 1)) {
            control.finishWithSuccess();
        } else {
            control.curTask = control.subtasks.elementAt(curPos + 1);
            if (!control.curTask.checkConditions()) {
                control.finishWithFailure();
            }
        }
    }
}
