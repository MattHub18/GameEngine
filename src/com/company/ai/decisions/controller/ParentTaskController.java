package com.company.ai.decisions.controller;

import com.company.ai.decisions.Task;

import java.util.Vector;

public class ParentTaskController extends TaskController {
    public Vector<Task> subtasks;
    public Task curTask;

    public ParentTaskController(Task task) {
        super(task);
        this.subtasks = new Vector<>();
        this.curTask = null;
    }

    public void add(Task task) {
        subtasks.add(task);
    }

    public void reset() {
        super.reset();
        this.curTask = subtasks.firstElement();
    }
}
