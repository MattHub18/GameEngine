package com.company.entities.human.movable;

import com.company.entities.human.Entity;

import java.io.Serializable;

public class AllDirectionalEntity extends MovableEntity implements Serializable {

    private final HorizontalEntity horizontalEntity;
    private final VerticalEntity verticalEntity;

    public AllDirectionalEntity(Entity entity) {
        super(entity);
        horizontalEntity = new HorizontalEntity(entity);
        verticalEntity = new VerticalEntity(entity);
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
