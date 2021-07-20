package com.company.entities;

import com.company.graphic.gfx.Rectangle;

public interface GameEntity {
    default Rectangle getBox() {
        return null;
    }

    void handleCollisionWith(GameEntity e);
}
