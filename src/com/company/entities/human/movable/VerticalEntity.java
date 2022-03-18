package com.company.entities.human.movable;

import com.company.directions.SystemFacingDirections;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;

import java.io.Serializable;

public class VerticalEntity extends MovableEntity implements Serializable {

    public VerticalEntity(Entity entity, EntityGraphicComponent component) {
        super(entity, component);
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

    @Override
    public GameEntity copy() {
        return new VerticalEntity((Entity) entity.copy(), component.copy());
    }
}
