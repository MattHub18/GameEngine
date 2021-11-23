package com.company.ai.movement;

import com.company.entities.human.GameEntity;

public interface MovementComponent {
    Point move(GameEntity aiEntity, int targetX, int targetY);
}
