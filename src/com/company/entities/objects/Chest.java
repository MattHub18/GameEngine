package com.company.entities.objects;

import com.company.entities.EntityManager;
import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.Room;

public abstract class Chest implements GameEntity, Interactive {

    protected final Entity entity;
    protected final ContentDisplay display;
    private boolean on;

    public Chest(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection, ContentDisplay display) {
        this.on = false;
        this.entity = new Entity(textureFilename, posX, posY, maxFrames, room, facingDirection);
        this.display = display;
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
        return entity.getFacingDirection();
    }

    @Override
    public void handleCollisionWith(Rectangle tileBox) {
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
        if (on) {
            display.show(r, this);
        }
    }

    @Override
    public void on() {
        on = true;
    }

    @Override
    public void off() {
        on = false;
        EntityManager entityManager = entity.getRoom().getEntityManager();
        entityManager.getEntities().removeIf(chest -> chest instanceof Chest && chest.equals(this));
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
