package com.company.entities;

import com.company.FAKE.FakeGame;
import com.company.directions.Direction;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Camera;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.Collider;
import com.company.physics.collisions.CollisionDetector;
import com.company.physics.primitives.AxisAlignedBoundingBox;

public abstract class Entity implements Collider, Graphic {
    protected byte entityID;
    protected int posX;
    protected int posY;

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected Direction facing;

    protected byte animationFrame;
    protected float animationDelay;

    protected boolean alive;

    public Entity(byte id, int posX, int posY, float delay) {
        this.entityID = id;

        this.posX = posX * FakeGame.TILE_WIDTH;
        this.posY = posY * FakeGame.TILE_HEIGHT;

        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

        this.facing = Direction.SOUTH;

        this.animationFrame = 0;
        this.animationDelay = delay;

        this.alive = true;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void move() {
        if (up) {
            posY--;
            this.facing = Direction.NORTH;
        }
        if (down) {
            posY++;
            this.facing = Direction.SOUTH;
        }
        if (left) {
            posX--;
            this.facing = Direction.WEST;
        }
        if (right) {
            posX++;
            this.facing = Direction.EAST;
        }
    }

    public void handleCollisionWith(Collider collider) {
        AxisAlignedBoundingBox body = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + FakeGame.TILE_WIDTH, posY + FakeGame.TILE_HEIGHT));

        if (!CollisionDetector.isCollided(body, collider))
            return;

        Vector distance = collider.getCenter().sub(body.getCenter());
        if (Math.abs(distance.getY()) < FakeGame.TILE_HEIGHT) {
            if (body.getCenter().getY() < collider.getCenter().getY())
                posY = (int) (collider.getCenter().getY() - FakeGame.HALF_TILE - FakeGame.TILE_HEIGHT);
            else
                posY = (int) (collider.getCenter().getY() + FakeGame.HALF_TILE) + 1;
        } else {
            if (body.getCenter().getX() < collider.getCenter().getX())
                posX = (int) (collider.getCenter().getX() - FakeGame.HALF_TILE - FakeGame.TILE_WIDTH);
            else
                posX = (int) (collider.getCenter().getX() + FakeGame.HALF_TILE) + 1;
        }
    }

    @Override
    public Vector getCenter() {
        return new Vector(posX + FakeGame.HALF_TILE, posY + FakeGame.HALF_TILE);
    }

    public void checkMovement(GameLoop gl) {
        throw new RuntimeException("OPERATION NOT PERMITTED");
    }

    public void registerEntityToCamera(Camera camera) {
        camera.setEntity(this);
    }

}
