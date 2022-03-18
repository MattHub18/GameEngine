package com.company.ai.decisions.single_task;

import com.company.entities.human.entity.GameEntity;
import com.company.entities.human.shooting.ShootingInterface;

public class ShootingTask extends LeafTask {
    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void doAction(GameEntity entity) {
        ((ShootingInterface) entity).shooting();
        control.finishWithSuccess();
    }

    @Override
    public void start() {
    }
}
