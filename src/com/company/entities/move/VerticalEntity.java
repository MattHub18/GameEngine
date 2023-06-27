package com.company.entities.move;

import com.company.entities.directions.SystemFacingDirections;
import com.company.entities.entity.Entity;
import com.company.entities.entity.EntityGraphicComponent;

public class VerticalEntity extends MovableEntity {

    public VerticalEntity(Entity entity, EntityGraphicComponent component, int velocity) {
        super(entity, component, velocity);
    }

    @Override
    public void moveUp() {
        super.moveUp();
        entity.setFacingDirection(SystemFacingDirections.NORTH);
    }

    @Override
    public void moveDown() {
        super.moveDown();
        entity.setFacingDirection(SystemFacingDirections.SOUTH);
    }
}
