package com.company.entities.human;

import com.company.graphic.Graphic;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

public interface GameEntity extends Graphic {

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
