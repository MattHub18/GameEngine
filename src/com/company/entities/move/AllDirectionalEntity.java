package com.company.entities.move;

import com.company.entities.entity.Entity;
import com.company.entities.entity.EntityGraphicComponent;

public class AllDirectionalEntity extends MovableEntity {

    private final HorizontalEntity horizontalEntity;
    private final VerticalEntity verticalEntity;

    public AllDirectionalEntity(Entity entity, EntityGraphicComponent component, int velocity) {
        super(entity, component, velocity);
        horizontalEntity = new HorizontalEntity(entity, component, velocity);
        verticalEntity = new VerticalEntity(entity, component, velocity);
    }

    @Override
    public void moveUp() {
        verticalEntity.moveUp();
    }

    @Override
    public void moveDown() {
        verticalEntity.moveDown();
    }

    @Override
    public void moveLeft() {
        horizontalEntity.moveLeft();
    }

    @Override
    public void moveRight() {
        horizontalEntity.moveRight();
    }

    @Override
    public void clearMove() {
        verticalEntity.clearMove();
        horizontalEntity.clearMove();
    }

    @Override
    public boolean isMoving() {
        return verticalEntity.isMoving() || horizontalEntity.isMoving();
    }
}
