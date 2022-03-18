package com.company.entities.human.entity;

import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import java.io.Serializable;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class EntityGraphicComponent implements Serializable {
    private final byte textureFilename;
    private final int maxFrames;
    private final int shiftIndex;

    private final float animationDelay;
    private float animationFrame;

    public EntityGraphicComponent(byte textureFilename, int maxFrames, int shiftIndex) {
        this.textureFilename = textureFilename;
        this.maxFrames = maxFrames;
        this.shiftIndex = shiftIndex;
        this.animationFrame = 0;
        this.animationDelay = 15;
    }

    private EntityGraphicComponent(byte textureFilename, int maxFrames, float animationDelay, float animationFrame, int shiftIndex) {
        this.textureFilename = textureFilename;
        this.maxFrames = maxFrames;
        this.animationDelay = animationDelay;
        this.animationFrame = animationFrame;
        this.shiftIndex = shiftIndex;
    }

    public EntityGraphicComponent copy() {
        return new EntityGraphicComponent(textureFilename, maxFrames, animationDelay, animationFrame, shiftIndex);
    }


    public void update(float dt) {
        animationFrame += dt * animationDelay;
        if (animationFrame >= maxFrames)
            animationFrame = 0;
    }


    public void render(Render r, int posX, int posY, byte facingDirection) {
        TileImage tileImage = new TileImage(Archive.TEXTURES.get(this.textureFilename), TILE_WIDTH, TILE_HEIGHT, false, true);
        r.addImage(tileImage.getTile(facingDirection + shiftIndex, (int) animationFrame), posX, posY);
    }

    public float getAnimationFrame() {
        return animationFrame;
    }

    public int getMaxFrames() {
        return maxFrames;
    }

    public byte getTextureFilename() {
        return textureFilename;
    }

    public int getShiftIndex() {
        return shiftIndex;
    }
}
