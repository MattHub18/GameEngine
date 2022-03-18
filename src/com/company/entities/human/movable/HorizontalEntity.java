package com.company.entities.human.movable;

import com.company.directions.SystemFacingDirections;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;

import java.io.Serializable;

public class HorizontalEntity extends MovableEntity implements Serializable {

    public HorizontalEntity(Entity entity, EntityGraphicComponent component) {
        super(entity, component);
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

    @Override
    public GameEntity copy() {
        return new HorizontalEntity((Entity) entity.copy(), component.copy());
    }
}
