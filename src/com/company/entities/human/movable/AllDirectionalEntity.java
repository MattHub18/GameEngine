package com.company.entities.human.movable;

import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;

import java.io.Serializable;

public class AllDirectionalEntity extends MovableEntity implements Serializable {

    private final HorizontalEntity horizontalEntity;
    private final VerticalEntity verticalEntity;

    public AllDirectionalEntity(Entity entity, EntityGraphicComponent component) {
        super(entity, component);
        horizontalEntity = new HorizontalEntity(entity, component);
        verticalEntity = new VerticalEntity(entity, component);
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

    @Override
    public GameEntity copy() {
        return new AllDirectionalEntity((Entity) entity.copy(), component.copy());
    }
}
