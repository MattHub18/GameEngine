package com.company.ai.decisions.single_task;


import com.company.entities.human.combat.CombatInterface;
import com.company.entities.human.entity.GameEntity;

public class AttackTask extends LeafTask {
    @Override
    public boolean checkConditions() {
        return true;
    }

    @Override
    public void doAction(GameEntity entity) {
        ((CombatInterface) entity).meleeAttack();
        control.finishWithSuccess();
    }

    @Override
    public void start() {
    }
}
