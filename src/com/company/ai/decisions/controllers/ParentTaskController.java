package com.company.ai.decisions.controllers;

import com.company.ai.decisions.Task;

import java.util.Vector;

public class ParentTaskController extends TaskController {
    private final Vector<Task> subtasks;
    private int index;

    public ParentTaskController() {
        this.subtasks = new Vector<>();
        this.index = -1;
    }

    public void add(Task task) {
        subtasks.add(task);
    }

    public void reset() {
        super.reset();
        this.index = -1;
    }

    public Task getCurTask() {
        return subtasks.get(index);
    }

    public int getSize() {
        return subtasks.size();
    }

    public Vector<Task> getSubtasks() {
        return subtasks;
    }

    public void start() {
        index = 0;
    }

    public boolean hasNext() {
        return index != subtasks.size() - 1;
    }

    public void next() {
        index += 1;
    }

    public void setTask(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
