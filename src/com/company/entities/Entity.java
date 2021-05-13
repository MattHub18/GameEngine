package com.company.entities;

import com.company.directions.Direction;
import com.company.entities.bullets.BulletMagazine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Camera;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.Collider;
import com.company.physics.collisions.CollisionDetector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.worlds.Map;
import com.company.worlds.Tile;

import java.io.Serializable;

public abstract class Entity implements Collider, Graphic, Serializable {
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

    private final int TILE_WIDTH = GameLoop.TILE_WIDTH;
    private final int TILE_HEIGHT = GameLoop.TILE_HEIGHT;

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

    public void checkCollision(Map map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tile t = map.getTile(x, y);
                handleCollisionWith(t);
            }
        }
    }

    private void handleCollisionWith(Tile tile) {
        body = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
        AxisAlignedBoundingBox tileBox = tile.getBox();

        if (!CollisionDetector.isCollided(tileBox, body) || tile.isFloor())
            return;

        AxisAlignedBoundingBox intersection = CollisionDetector.intersection(body, tileBox);

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

    @Override
    public Vector getCenter() {
        return new Vector(posX + TILE_WIDTH / 2f, posY + TILE_HEIGHT / 2f);
    }

    public void shooting(GameLoop gl) {
        throw new RuntimeException("OPERATION NOT PERMITTED");
    }

    public void registerBulletMagazine(BulletMagazine m) {
        throw new RuntimeException("OPERATION NOT PERMITTED");
    }

    public void registerEntityToCamera(Camera camera) {
        camera.setEntity(this);
    }

    public Direction getFacing() {
        return facing;
    }
}
