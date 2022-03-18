package com.company.ai.decisions.single_task;


import com.company.ai.AiInterface;
import com.company.entities.human.entity.GameEntity;

public class SelectTargetCombatTask extends LeafTask {
    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void doAction(GameEntity entity) {
        GameEntity ce = ((AiInterface) entity).chooseCombatTarget();
        if (ce != null) {
            queue.add(ce);
            control.finishWithSuccess();
        } else
            control.finishWithFailure();
    }

    @Override
    public void start() {
    }
}
