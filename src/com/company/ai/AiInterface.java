package com.company.ai;

import com.company.entities.entity.GameEntity;
import com.company.physics.basics.Point;

public interface AiInterface {
    Point chooseMoveTarget();

    GameEntity chooseCombatTarget();
}
