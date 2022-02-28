package com.company.ai.movement;

import com.company.entities.human.GameEntity;
import com.company.physics.basics.Point;

public interface MovementComponent {
    Point move(GameEntity aiEntity, int targetX, int targetY);
}
