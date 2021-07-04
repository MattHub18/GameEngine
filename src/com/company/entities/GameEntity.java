package com.company.entities;

import com.company.world.Tile;

public interface GameEntity {
    void handleCollisionWith(Tile t);

    void handleCollisionWith(GameEntity e);
}
