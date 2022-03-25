package com.company.ai.decisions.single_task;


import com.company.ai.AiInterface;
import com.company.entities.human.combat.CombatInterface;
import com.company.entities.human.entity.GameEntity;

public class AttackTask extends LeafTask {

    private final double value;

    public AttackTask(double value) {
        this.value = value;
    }

    @Override
    public boolean checkConditions(AiInterface entity) {
        GameEntity target = entity.chooseCombatTarget();
        return target != null;
    }

    @Override
    public void doAction(GameEntity entity) {
        ((CombatInterface) entity).meleeAttack();
        control.finishWithSuccess();
    }

    @Override
    public double getValue(AiInterface entity) {
        return value;
    }
}
