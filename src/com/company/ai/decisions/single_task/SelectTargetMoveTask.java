package com.company.ai.decisions.single_task;

import com.company.ai.AiInterface;
import com.company.ai.movement.Point;
import com.company.entities.human.GameEntity;

public class SelectTargetMoveTask extends LeafTask {

    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void doAction(GameEntity entity) {
        Point t = ((AiInterface) entity).chooseMoveTarget();
        if (t != null) {
            queue.add(t);
            control.finishWithSuccess();
        } else
            control.finishWithFailure();
    }

    @Override
    public void start() {
    }
}
