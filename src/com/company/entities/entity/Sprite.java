package com.company.entities.entity;

import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Sprite {
    private final TileImage texture;
    private final int maxFrames;
    private final int row;
    private final float animationDelay;
    private float animationFrame;

    public Sprite(byte textureFilename, int maxFrames, int row) {
        this.texture = new TileImage(Archive.TEXTURES.get(textureFilename), TILE_WIDTH, TILE_HEIGHT, false, true);
        this.maxFrames = maxFrames;
        this.row = row;
        this.animationFrame = 0;
        this.animationDelay = 15;
    }

    public void update(float dt) {
        animationFrame = (animationFrame + dt * animationDelay) % maxFrames;
    }


    public void render(Render r, int posX, int posY, byte facingDirection) {
        r.addImage(texture.getTile(facingDirection + row, (int) animationFrame), posX, posY);
    }

    public boolean endAnimation(float dt) {
        return animationFrame <= dt * animationDelay;
    }
}
