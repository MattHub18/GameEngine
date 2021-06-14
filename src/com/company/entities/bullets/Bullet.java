package com.company.entities.bullets;

import com.company.directions.Direction;
import com.company.entities.GameEntity;
import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.resources.Resources;
import com.company.worlds.Tile;

import java.io.Serializable;

public abstract class Bullet implements Graphic, Serializable, GameEntity {
    protected int posX;
    protected int posY;
    protected Direction facing;
    protected float animationDelay;
    protected int maxRange;
    protected AxisAlignedBoundingBox box;

    public Bullet(Entity player, float delay, int maxR) {
        posX = player.getPosX();
        posY = player.getPosY();
        facing = player.getFacing();
        animationDelay = delay;
        maxRange = maxR;
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
    }

    @Override
    public void update(GameLoop gl, float dt) {
        maxRange -= dt * animationDelay;
        switch (facing) {
            case NORTH:
                posY -= dt * animationDelay;
                break;
            case SOUTH:
                posY += dt * animationDelay;
                break;
            case WEST:
                posX -= dt * animationDelay;
                break;
            case EAST:
                posX += dt * animationDelay;
                break;
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage player = new TileImage(Resources.TEXTURES.get(Resources.BULLET), GameLoop.TILE_WIDTH, GameLoop.TILE_HEIGHT);
        r.addImage(player.getTile(facingDirection(), 0), posX, posY);
    }

    private byte facingDirection() {
        switch (facing) {
            case NORTH:
                return Resources.BULLET_BACK;
            case SOUTH:
                return Resources.BULLET_FRONT;
            case WEST:
                return Resources.BULLET_LEFT;
            case EAST:
                return Resources.BULLET_RIGHT;
            default:
                return 0;
        }
    }

    @Override
    public void handleCollisionWith(Tile tile) {
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
        AxisAlignedBoundingBox tileBox = tile.getBox();

        if (!CollisionDetector.isCollided(tileBox, box) || tile.isFloor())
            return;

        posX = -GameLoop.TILE_WIDTH;
        posY = -GameLoop.TILE_HEIGHT;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
