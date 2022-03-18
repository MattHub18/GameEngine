package com.company.entities.human.movable;

import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

import java.io.Serializable;

public abstract class MovableEntity implements Graphic, GameEntity, MovableInterface, Serializable {

    private final int velocity;
    protected final Entity entity;
    protected final EntityGraphicComponent component;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;


    public MovableEntity(Entity entity, EntityGraphicComponent component) {
        this.entity = entity;
        this.component = component;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.velocity = 1;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (component != null)
            component.update(dt);
        else
            entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (component != null)
            component.render(r, entity.getPosX(), entity.getPosY(), entity.getFacingDirection());
        else
            entity.render(gl, r);
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
    public AxisAlignedBoundingBox getBox() {
        return entity.getBox();
    }

    @Override
    public byte getFacingDirection() {
        return entity.getFacingDirection();
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
        entity.handleCollisionWith(tileBox);
    }

    @Override
    public void moveUp() {
        up = true;
        int posY = entity.getPosY();
        posY -= velocity;
        entity.setPosY(posY);
    }

    @Override
    public void moveDown() {
        down = true;
        int posY = entity.getPosY();
        posY += velocity;
        entity.setPosY(posY);
    }

    @Override
    public void moveLeft() {
        left = true;
        int posX = entity.getPosX();
        posX -= velocity;
        entity.setPosX(posX);
    }

    @Override
    public void moveRight() {
        right = true;
        int posX = entity.getPosX();
        posX += velocity;
        entity.setPosX(posX);
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
        return ((int) component.getAnimationFrame()) == component.getMaxFrames() / 2 - 1 || ((int) component.getAnimationFrame()) == component.getMaxFrames() - 1;
    }
}
