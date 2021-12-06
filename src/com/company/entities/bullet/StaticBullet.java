package com.company.entities.bullet;

import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.collisions.CollisionDetector;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.directions.FacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class StaticBullet implements GameEntity, Serializable {
    protected final byte facingDirection;
    private final int damage;
    private final float animationDelay;
    protected Entity entity = null;
    protected int maxTime;

    public StaticBullet(byte textureFilename, int posX, int posY, int maxFrames, Room room, int maxTime, int damage, byte facingDirection, float animationDelay) {
        this.maxTime = maxTime;
        this.damage = damage;
        this.facingDirection = facingDirection;
        this.animationDelay = animationDelay;
        switchingDirection(facingDirection, textureFilename, posX, posY, maxFrames, room);
    }

    private void switchingDirection(byte facingDirection, byte textureFilename, int posX, int posY, int maxFrames, Room room) {
        switch (facingDirection) {
            case NORTH:
                posY -= TILE_HEIGHT();
                break;
            case SOUTH:
                posY += TILE_HEIGHT();
                break;
            case WEST:
                posX -= TILE_WIDTH();
                break;
            case EAST:
                posX += TILE_WIDTH();
                break;
        }

        this.entity = new Entity(textureFilename, posX, posY, maxFrames, room, facingDirection);
    }

    @Override
    public int getPosX() {
        return entity.getPosX();
    }

    @Override
    public void setPosX(int posX) {
        entity.setPosX(posX);
    }

    @Override
    public int getPosY() {
        return entity.getPosY();
    }

    @Override
    public void setPosY(int posY) {
        entity.setPosY(posY);
    }

    @Override
    public Room getRoom() {
        return entity.getRoom();
    }

    @Override
    public void setRoom(Room room) {
        entity.setRoom(room);
    }

    @Override
    public Rectangle getBox() {
        return entity.getBox();
    }

    @Override
    public byte getFacingDirection() {
        return facingDirection;
    }

    @Override
    public void handleCollisionWith(Rectangle tileBox) {
        entity.updateBox();

        if (!CollisionDetector.isCollided(tileBox, entity.getBox()))
            return;
        maxTime = 0;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        updateTime(dt);
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }

    protected void updateTime(float dt) {
        maxTime -= dt * animationDelay;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getDamage() {
        return damage;
    }
}
