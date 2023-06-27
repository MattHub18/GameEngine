package com.company.entities.entity;

import com.company.physics.collisions.AxisAlignedBoundingBox;
import com.company.worlds.Ambient;

import java.util.HashMap;

public interface GameEntity {

    int getPosX();

    void setPosX(int posX);

    int getPosY();

    void setPosY(int posY);

    Ambient getRoom();

    void setRoom(Ambient ambient);

    AxisAlignedBoundingBox getBox();

    byte getFacingDirection();

    void handleCollisionWith(AxisAlignedBoundingBox tileBox);

    String serialize();

    HashMap<String, String> deserialize(String serial);
}
