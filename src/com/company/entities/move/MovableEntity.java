package com.company.entities.move;

import com.company.entities.entity.Entity;
import com.company.entities.entity.EntityGraphicComponent;
import com.company.entities.entity.GameEntity;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;
import com.company.physics.collisions.AxisAlignedBoundingBox;
import com.company.worlds.Ambient;

import java.util.HashMap;

public abstract class MovableEntity implements Graphic, GameEntity, MovableInterface {

    protected final Entity entity;
    protected final EntityGraphicComponent component;
    private final int velocity;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;


    public MovableEntity(Entity entity, EntityGraphicComponent component, int velocity) {
        this.entity = entity;
        this.component = component;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.velocity = velocity;
    }

    @Override
    public void update(Engine engine, float dt) {
        if (component != null)
            component.update(dt);
        else
            entity.update(engine, dt);
    }

    @Override
    public void render(Render r) {
        if (component != null)
            component.render(r, entity.getPosX(), entity.getPosY(), entity.getFacingDirection());
        else
            entity.render(r);
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
    public Ambient getRoom() {
        return entity.getRoom();
    }

    @Override
    public void setRoom(Ambient ambient) {
        entity.setRoom(ambient);
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

    @Override
    public String serialize() {
        return null;
    }

    @Override
    public HashMap<String, String> deserialize(String serial) {
        return null;
    }
}
