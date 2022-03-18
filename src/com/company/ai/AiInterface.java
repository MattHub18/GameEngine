package com.company.ai;


import com.company.entities.human.entity.GameEntity;
import com.company.physics.basics.Point;

public interface AiInterface {
    default Point chooseMoveTarget() {
        throw new UnsupportedOperationException();
    }

    default GameEntity chooseCombatTarget() {
        throw new UnsupportedOperationException();
    }
}
