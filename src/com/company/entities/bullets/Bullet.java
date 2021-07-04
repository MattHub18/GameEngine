package com.company.entities.bullets;

import com.company.directions.Direction;
import com.company.directions.FacingDirections;
import com.company.entities.GameEntity;
import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.resources.Resources;
import com.company.world.Tile;

import java.io.Serializable;

public abstract class Bullet implements Graphic, Serializable, GameEntity {
    protected int posX;
    protected int posY;
    protected Direction facing;
    protected float animationDelay;
    protected float animationFrame;
    protected int maxRange;
    protected Rectangle box;
    protected int maxFrames;
    protected byte textureFilename;
    protected byte facingDirection;

    public Bullet(Entity player, byte filename, int maxR, int maxF, float delay) {
        this.posX = player.getPosX();
        this.posY = player.getPosY();
        this.facing = player.getFacing();
        this.animationDelay = delay;
        this.textureFilename = filename;
        this.animationFrame = 0;
        this.maxFrames = maxF;
        maxRange = maxR;
        box = new Rectangle(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT), 0xff000000, false);
    }

    @Override
    public void update(GameLoop gl, float dt) {
        move(dt);

        animationFrame += dt * animationDelay;
        if (animationFrame > maxFrames)
            animationFrame = 1;
    }

    private void move(float dt) {
        maxRange -= dt * animationDelay;
        switch (facing) {
            case NORTH:
                posY -= dt * animationDelay;
                facingDirection = FacingDirections.BACK.value;
                break;
            case SOUTH:
                posY += dt * animationDelay;
                facingDirection = FacingDirections.FRONT.value;
                break;
            case WEST:
                posX -= dt * animationDelay;
                facingDirection = FacingDirections.LEFT.value;
                break;
            case EAST:
                posX += dt * animationDelay;
                facingDirection = FacingDirections.RIGHT.value;
                break;
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage player = new TileImage(Resources.TEXTURES.get(textureFilename), posX, posY, GameLoop.TILE_WIDTH, GameLoop.TILE_HEIGHT);
        r.addImage(player.getTile(facingDirection, (int) animationFrame));
    }

    @Override
    public void handleCollisionWith(Tile tile) {
        box = new Rectangle(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT), 0xff000000, false);
        Rectangle tileBox = tile.getBox();

        if (CollisionDetector.isCollided(tileBox, box) || tile.isFloor())
            return;

        posX = -GameLoop.TILE_WIDTH;
        posY = -GameLoop.TILE_HEIGHT;
    }

    @Override
    public void handleCollisionWith(GameEntity e) {
        //TODO IMPLEMENT ME
    }

    public int getMaxRange() {
        return maxRange;
    }
}
