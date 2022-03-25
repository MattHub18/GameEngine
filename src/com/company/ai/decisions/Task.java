package com.company.ai.decisions;

import com.company.ai.AiInterface;
import com.company.ai.decisions.controller.TaskController;
import com.company.entities.human.entity.GameEntity;

public interface Task {

    boolean checkConditions(AiInterface entity);

    void doAction(GameEntity entity);

    TaskController getController();

    void start(AiInterface entity);

    double getValue(AiInterface entity);

    void reset();
}

