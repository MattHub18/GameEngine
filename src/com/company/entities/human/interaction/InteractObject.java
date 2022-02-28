package com.company.entities.human.interaction;

import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.entities.objects.Interactive;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

public abstract class InteractObject implements GameEntity, Interactive {
    protected final Entity entity;
    protected boolean on;

    public InteractObject(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection) {
        this.on = false;
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
    public AxisAlignedBoundingBox getBox() {
        return entity.getBox();
    }

    @Override
    public byte getFacingDirection() {
        return entity.getFacingDirection();
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }

    @Override
    public void on() {
        on = true;
    }

    @Override
    public void off() {
        on = false;
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
