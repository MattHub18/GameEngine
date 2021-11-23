package com.company.entities.human.movable;

import com.company.directions.FacingDirections;
import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;

public class MovableEntity implements Graphic, MovableInterface, Serializable {

    private final int velocity;
    private final Entity entity;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;


    public MovableEntity(Entity entity) {
        this.entity = entity;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.velocity = 1;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (isMoving())
            entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (!isMoving()) {
            entity.setAnimationFrame(0);
            entity.render(gl, r);
        } else {
            byte amount = FacingDirections.TOTAL_DIRECTION;
            entity.incrementFacingDirection(amount);
            entity.render(gl, r);
            entity.decrementFacingDirection(amount);
        }
    }

    @Override
    public void moveUp() {
        up = true;
        entity.decrementPosY(velocity);
        entity.setFacingDirection(FacingDirections.NORTH);
    }

    @Override
    public void moveDown() {
        down = true;
        entity.incrementPosY(velocity);
        entity.setFacingDirection(FacingDirections.SOUTH);
    }

    @Override
    public void moveLeft() {
        left = true;
        entity.decrementPosX(velocity);
        entity.setFacingDirection(FacingDirections.WEST);
    }

    @Override
    public void moveRight() {
        right = true;
        entity.incrementPosX(velocity);
        entity.setFacingDirection(FacingDirections.EAST);
    }

    @Override
    public void clearMove() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    @Override
    public boolean isMoving() {
        return up || down || left || right;
    }

    public boolean halfCycle() {
        return ((int) entity.getAnimationFrame()) == entity.getMaxFrames() / 2 - 1 || ((int) entity.getAnimationFrame()) == entity.getMaxFrames() - 1;
    }
}