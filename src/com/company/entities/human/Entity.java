package com.company.entities.human;

import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.resources.file_system.Archive;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Entity implements GameEntity, Serializable {
    private final float animationDelay;
    private final byte textureFilename;
    private int posX;
    private int posY;
    private byte facingDirection;
    private float animationFrame;
    private int maxFrames;
    private AxisAlignedBoundingBox box;

    private Room room;

    public Entity(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection) {
        this.textureFilename = textureFilename;

        this.posX = posX;
        this.posY = posY;

        this.facingDirection = facingDirection;

        this.animationFrame = 0;
        this.animationDelay = 15;
        this.maxFrames = maxFrames;

        this.box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + TILE_WIDTH, posY + TILE_HEIGHT));

        this.room = room;
    }

    private Entity(float animationDelay, byte textureFilename, int posX, int posY, byte facingDirection, float animationFrame, int maxFrames, AxisAlignedBoundingBox box, Room room) {
        this.animationDelay = animationDelay;
        this.textureFilename = textureFilename;
        this.posX = posX;
        this.posY = posY;
        this.facingDirection = facingDirection;
        this.animationFrame = animationFrame;
        this.maxFrames = maxFrames;
        this.box = box;
        this.room = room;
    }

    @Override
    public GameEntity copy() {
        return new Entity(animationDelay, textureFilename, posX, posY, facingDirection, animationFrame, maxFrames, box, room);
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
    public void update(GameLoop gl, float dt) {
        animationFrame += dt * animationDelay;
        if (animationFrame >= maxFrames)
            animationFrame = 0;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage tileImage = new TileImage(Archive.TEXTURES.get(this.textureFilename), TILE_WIDTH, TILE_HEIGHT, false, true);
        r.addImage(tileImage.getTile(facingDirection, (int) animationFrame), posX, posY);
    }

    @Override
    public AxisAlignedBoundingBox getBox() {
        return box;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    public void updateBox() {
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + TILE_WIDTH, posY + TILE_HEIGHT));
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
        updateBox();

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

    public float getAnimationFrame() {
        return animationFrame;
    }

    public void setAnimationFrame(float animationFrame) {
        this.animationFrame = animationFrame;
    }

    public void incrementFacingDirection(byte amount) {
        facingDirection += amount;
    }

    public void decrementFacingDirection(byte amount) {
        facingDirection -= amount;
    }

    public void incrementPosX(int amount) {
        posX += amount;
    }

    public void decrementPosX(int amount) {
        posX -= amount;
    }

    public void incrementPosY(int amount) {
        posY += amount;
    }

    public void decrementPosY(int amount) {
        posY -= amount;
    }

    public int getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(int maxFrames) {
        this.maxFrames = maxFrames;
    }

    public byte getTextureFilename() {
        return textureFilename;
    }
}
