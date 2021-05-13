package com.company.entities.bullets;

import com.company.directions.Direction;
import com.company.entities.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.worlds.Tile;

import java.io.Serializable;

public abstract class Bullet implements Graphic, Serializable {
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

    public boolean handleCollisionWith(Tile tile) {
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
        AxisAlignedBoundingBox tileBox = tile.getBox();

        return !(!CollisionDetector.isCollided(tileBox, box) || tile.isFloor());
    }

    public int getMaxRange() {
        return maxRange;
    }
}
