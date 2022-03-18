package com.company.ai.decisions;

import com.company.ai.decisions.controller.TaskController;
import com.company.entities.human.entity.GameEntity;

import java.util.LinkedList;
import java.util.Queue;

public interface Task {
    Queue<Object> queue = new LinkedList<>();

    boolean checkConditions();

    void doAction(GameEntity entity);

    TaskController getControl();

    void start();
}

