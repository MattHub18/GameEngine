package com.company.entities.objects;

import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.Room;

public class Chest implements GameEntity {

    private final Entity entity;
    private final ContentDisplayer displayer;
    private Content contents;
    private boolean open;

    public Chest(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection, ContentDisplayer displayer) {
        this.contents = null;
        this.open = false;
        this.entity = new Entity(textureFilename, posX, posY, maxFrames, room, facingDirection);
        this.displayer = displayer;
    }

    public Content getContents() {
        return contents;
    }

    public void setContents(Content contents) {
        this.contents = contents;
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
    public GameEntity copy() {
        return new Chest(entity.getTextureFilename(), entity.getPosX(), entity.getPosY(), entity.getMaxFrames(), entity.getRoom(), entity.getFacingDirection(), displayer);
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
        if (open) {
            displayer.show(r, contents);
        }
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
