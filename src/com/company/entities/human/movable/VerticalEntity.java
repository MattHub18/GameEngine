package com.company.entities.human.movable;

import com.company.entities.human.Entity;

import java.io.Serializable;

import static com.company.directions.SystemFacingDirections.NORTH;
import static com.company.directions.SystemFacingDirections.SOUTH;

public class VerticalEntity extends MovableEntity implements Serializable {

    public VerticalEntity(Entity entity) {
        super(entity);
    }

    @Override
    public void moveUp() {
        super.moveUp();
        entity.setFacingDirection(NORTH);
    }

    @Override
    public void moveDown() {
        super.moveDown();
        entity.setFacingDirection(SOUTH);
    }
}
