package com.company.entities.bullet;

import com.company.entities.human.movable.AllDirectionalEntity;
import com.company.entities.human.movable.MovableInterface;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.directions.SystemFacingDirections.*;

public abstract class Bullet extends StaticBullet implements MovableInterface, Serializable {
    private final AllDirectionalEntity movableEntity;

    public Bullet(byte textureFilename, int posX, int posY, int maxFrames, Room room, int maxTime, int damage, byte facingDirection, float animationDelay) {
        super(textureFilename, posX, posY, maxFrames, room, maxTime, damage, facingDirection, animationDelay);
        this.movableEntity = new AllDirectionalEntity(entity);
    }

    @Override
    public void moveUp() {
        movableEntity.moveUp();
    }

    @Override
    public void moveDown() {
        movableEntity.moveDown();
    }

    @Override
    public void moveLeft() {
        movableEntity.moveLeft();
    }

    @Override
    public void moveRight() {
        movableEntity.moveRight();
    }

    @Override
    public boolean isMoving() {
        return movableEntity.isMoving();
    }

    @Override
    public void clearMove() {
        movableEntity.clearMove();
    }


    @Override
    public void update(GameLoop gl, float dt) {
        updateTime(dt);
        move();
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }

    private void move() {
        if (facingDirection == NORTH)
            moveUp();
        else if (facingDirection == SOUTH)
            moveDown();
        else if (facingDirection == WEST)
            moveLeft();
        else if (facingDirection == EAST)
            moveRight();
    }
}