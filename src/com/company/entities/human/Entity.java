package com.company.entities.human;

import com.company.directions.Direction;
import com.company.entities.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Camera;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.worlds.Tile;

import java.io.Serializable;
import java.util.Random;

public abstract class Entity implements Graphic, Serializable, GameEntity {
    protected byte entityID;
    protected int posX;
    protected int posY;

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected Direction facing;

    protected float animationFrame;
    protected float animationDelay;

    protected boolean alive;

    protected AxisAlignedBoundingBox body;

    protected final int TILE_WIDTH = GameLoop.TILE_WIDTH;
    protected final int TILE_HEIGHT = GameLoop.TILE_HEIGHT;

    private final int uniqueId;

    public Entity(byte id, int posX, int posY, float delay) {
        this.entityID = id;

        this.posX = posX * TILE_WIDTH;
        this.posY = posY * TILE_HEIGHT;

        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

        this.facing = Direction.SOUTH;

        this.animationFrame = 0;
        this.animationDelay = delay;

        this.alive = true;

        this.uniqueId = new Random().nextInt();
    }

    public Entity(Entity copy) {
        this.entityID = copy.entityID;

        this.posX = copy.posX;
        this.posY = copy.posY;

        this.up = copy.up;
        this.down = copy.down;
        this.left = copy.left;
        this.right = copy.right;

        this.facing = copy.facing;

        this.animationFrame = copy.animationFrame;
        this.animationDelay = copy.animationDelay;

        this.alive = copy.alive;

        this.body = copy.body;
        this.uniqueId = copy.uniqueId;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getUniqueId() {
        return uniqueId;
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

    public void handleCollisionWith(Tile tile) {
        body = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
        AxisAlignedBoundingBox tileBox = tile.getBox();

        if (!CollisionDetector.isCollided(tileBox, body) || tile.isFloor())
            return;

        AxisAlignedBoundingBox intersection = CollisionDetector.intersection(body, tileBox);

        if (intersection != null) {
            if (intersection.getWidth() > intersection.getHeight()) {

                if (posY < tileBox.getMin().getY())
                    posY = (int) (tileBox.getMin().getY() - body.getHeight());
                else
                    posY = (int) (tileBox.getMin().getY() + body.getHeight());
            } else {
                if (posX < tileBox.getMin().getX())
                    posX = (int) (tileBox.getMin().getX() - body.getWidth());
                else
                    posX = (int) (tileBox.getMin().getX() + body.getWidth());
            }
        }
    }

    public void registerEntityToCamera(Camera camera) {
        camera.setEntity(this);
    }

    public Direction getFacing() {
        return facing;
    }
}
