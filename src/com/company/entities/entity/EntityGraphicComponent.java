package com.company.entities.entity;

import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class EntityGraphicComponent {
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
}
