package com.company.ai.decisions;

import com.company.ai.AiInterface;
import com.company.ai.decisions.controllers.TaskController;
import com.company.entities.entity.GameEntity;

public interface Task {
    boolean checkConditions(AiInterface entity);

    void doAction(GameEntity entity);

    TaskController getController();

    void start(AiInterface entity);

    double getValue(AiInterface entity);

    void reset();
}

