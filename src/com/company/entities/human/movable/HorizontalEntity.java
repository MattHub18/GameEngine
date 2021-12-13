package com.company.entities.human.movable;

import com.company.entities.human.Entity;

import java.io.Serializable;

import static com.company.directions.SystemFacingDirections.EAST;
import static com.company.directions.SystemFacingDirections.WEST;

public class HorizontalEntity extends MovableEntity implements Serializable {

    public HorizontalEntity(Entity entity) {
        super(entity);
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        entity.setFacingDirection(WEST());
    }

    @Override
    public void moveRight() {
        super.moveRight();
        entity.setFacingDirection(EAST());
    }
}
