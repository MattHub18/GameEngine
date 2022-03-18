package com.company.entities.human.entity;

import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

public interface GameEntity {

    int getPosX();

    void setPosX(int posX);

    int getPosY();

    void setPosY(int posY);

    GameEntity copy();

    Room getRoom();

    void setRoom(Room room);

    AxisAlignedBoundingBox getBox();

    byte getFacingDirection();

    void handleCollisionWith(AxisAlignedBoundingBox tileBox);
}
