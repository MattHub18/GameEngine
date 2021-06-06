package com.company.entities;

import com.company.worlds.Tile;

public interface GameEntity {
    void handleCollisionWith(Tile t);
}
