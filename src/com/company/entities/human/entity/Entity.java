package com.company.entities.human.entity;

import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Entity implements GameEntity, Graphic, Serializable {

    protected final EntityGraphicComponent component;
    private final AxisAlignedBoundingBox box;
    protected int posX;
    protected int posY;

    private Room room;
    protected byte facingDirection;

    public Entity(int posX, int posY, byte facingDirection, Room room, EntityGraphicComponent component) {
        this.posX = posX;
        this.posY = posY;

        this.facingDirection = facingDirection;

        this.box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + TILE_WIDTH, posY + TILE_HEIGHT));

        this.room = room;

        this.component = component;
    }

    private Entity(int posX, int posY, byte facingDirection, AxisAlignedBoundingBox box, Room room, EntityGraphicComponent component) {
        this.posX = posX;
        this.posY = posY;
        this.facingDirection = facingDirection;
        this.box = box;
        this.room = room;
        this.component = component;
    }

    @Override
    public GameEntity copy() {
        return new Entity(posX, posY, facingDirection, box, room, component.copy());
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public AxisAlignedBoundingBox getBox() {
        return new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + TILE_WIDTH, posY + TILE_HEIGHT));
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public byte getFacingDirection() {
        return facingDirection;
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
        AxisAlignedBoundingBox box = getBox();

        if (!CollisionDetector.isCollided(tileBox, box))
            return;

        AxisAlignedBoundingBox intersection = CollisionDetector.intersection(box, tileBox);

        if (intersection != null) {
            if (intersection.getWidth() > intersection.getHeight()) {

                if (posY < tileBox.getMin().getY())
                    posY = ((int) (tileBox.getMin().getY() - box.getHeight()));
                else
                    posY = ((int) (tileBox.getMin().getY() + box.getHeight()));
            } else {
                if (posX < tileBox.getMin().getX())
                    posX = ((int) (tileBox.getMin().getX() - box.getWidth()));
                else
                    posX = ((int) (tileBox.getMin().getX() + box.getWidth()));
            }
        }
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (component != null)
            component.update(dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (component != null)
            component.render(r, posX, posY, facingDirection);
    }

    public void setFacingDirection(byte facingDirection) {
        this.facingDirection = facingDirection;
    }
}