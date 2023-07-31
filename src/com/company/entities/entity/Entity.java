package com.company.entities.entity;

import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.AxisAlignedBoundingBox;
import com.company.physics.collisions.CollisionDetector;
import com.company.util.Prototype;
import com.company.util.Serializable;
import com.company.worlds.Ambient;

import java.util.HashMap;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Entity implements GameEntity, Graphic, Prototype, Serializable {
    private final Sprite sprite;
    private int posX;
    private int posY;
    private Ambient ambient;
    private byte facingDirection;

    public Entity(int posX, int posY, byte facingDirection, Ambient ambient, Sprite sprite) {
        this.posX = posX;
        this.posY = posY;
        this.facingDirection = facingDirection;
        this.ambient = ambient;
        this.sprite = sprite;
    }

    public Entity(String serial, Ambient ambient, Sprite sprite) {
        HashMap<String, String> serialSplit = deserialize(serial);

        this.posX = Integer.parseInt(serialSplit.get("posX"));
        this.posY = Integer.parseInt(serialSplit.get("posY"));
        this.facingDirection = Byte.parseByte(serialSplit.get("facingDirection"));
        this.ambient = ambient;
        this.sprite = sprite;
    }

    protected Entity(Entity entity, Sprite sprite) {
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.facingDirection = entity.facingDirection;
        this.ambient = entity.ambient;
        this.sprite = sprite;
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
    public Ambient getAmbient() {
        return ambient;
    }

    @Override
    public void setAmbient(Ambient ambient) {
        this.ambient = ambient;
    }

    @Override
    public byte getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(byte facingDirection) {
        this.facingDirection = facingDirection;
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
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void update(Engine engine, float dt) {
        sprite.update(dt);
    }

    @Override
    public void render(Render r) {
        sprite.render(r, posX, posY, facingDirection);
    }

    @Override
    public Prototype copy() {
        return new Entity(this, this.sprite);
    }

    @Override
    public String serialize() {
        return "Entity={" +
                "posX:" + posX +
                ",posY:" + posY +
                ",facingDirection:" + facingDirection +
                ",ambient:" + ambient.getClass().getSimpleName() +
                "};";
    }

    @Override
    public HashMap<String, String> deserialize(String serial) {
        HashMap<String, String> serialSplit = new HashMap<>();
        String[] serials = serial.split(",");
        for (String pair : serials) {
            String[] keyValue = pair.split(":");
            serialSplit.put(keyValue[0], keyValue[1]);
        }
        return serialSplit;
    }
}