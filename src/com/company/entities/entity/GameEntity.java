package com.company.entities.entity;

import com.company.physics.collisions.AxisAlignedBoundingBox;
import com.company.worlds.Ambient;

public interface GameEntity {

    int getPosX();

    void setPosX(int posX);

    int getPosY();

    void setPosY(int posY);

    Ambient getAmbient();

    void setAmbient(Ambient ambient);

    AxisAlignedBoundingBox getBox();

    byte getFacingDirection();

    void handleCollisionWith(AxisAlignedBoundingBox tileBox);

    Sprite getSprite();
}
