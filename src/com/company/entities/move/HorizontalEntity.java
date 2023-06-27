package com.company.entities.move;

import com.company.entities.directions.SystemFacingDirections;
import com.company.entities.entity.Entity;
import com.company.entities.entity.EntityGraphicComponent;

public class HorizontalEntity extends MovableEntity {

    public HorizontalEntity(Entity entity, EntityGraphicComponent component, int velocity) {
        super(entity, component, velocity);
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        entity.setFacingDirection(SystemFacingDirections.WEST);
    }

    @Override
    public void moveRight() {
        super.moveRight();
        entity.setFacingDirection(SystemFacingDirections.EAST);
    }
}
