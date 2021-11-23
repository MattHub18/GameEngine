package com.company.ai;


import com.company.ai.movement.Point;
import com.company.entities.human.GameEntity;

public interface AiInterface {
    default Point chooseMoveTarget() {
        throw new UnsupportedOperationException();
    }

    default GameEntity chooseCombatTarget() {
        throw new UnsupportedOperationException();
    }
}
