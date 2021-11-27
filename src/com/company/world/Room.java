package com.company.world;

import com.company.entities.EntityManager;
import com.company.entities.human.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import java.io.Serializable;

import static com.company.resources.AbstractConstants.TILE_HEIGHT;
import static com.company.resources.AbstractConstants.TILE_WIDTH;

public abstract class Room implements Graphic, Serializable {
    private final int roomId;
    public int LEFT;
    public int RIGHT;
    public int UP;
    public int DOWN;
    protected int width;
    protected int height;
    protected EntityManager entityManager;
    protected transient boolean visited;
    private byte[][] tiles;
    private int withInPixel;
    private int heightInPixel;
    private final byte textureFilename;

    public Room(byte textureFilename, int roomId, EntityManager entityManager) {
        this.roomId = roomId;
        this.textureFilename = textureFilename;
        this.entityManager = entityManager;
        visited = false;
    }

    protected void build(byte[][] tiles) {
        this.tiles = tiles;
        this.width = tiles[0].length;
        this.height = tiles.length;

        withInPixel = width * TILE_WIDTH();
        heightInPixel = height * TILE_HEIGHT();
    }

    public Tile getTile(int x, int y) {
        return new Tile(tiles[y][x], x, y);
    }

    @Override
    public void update(GameLoop gl, float dt) {
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage roomTextures = new TileImage(Archive.TEXTURES.get(textureFilename), TILE_WIDTH(), TILE_HEIGHT(), false, true);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                r.addImage(roomTextures.getTile(roomId, tiles[y][x]), x * TILE_WIDTH(), y * TILE_HEIGHT());
            }
        }
    }

    public int getWidthInPixel() {
        return withInPixel;
    }

    public int getHeightInPixel() {
        return heightInPixel;
    }

    public void collisions(GameEntity e) {
        tileCollision(e);
        entityManager.entityCollision(e);
    }

    private void tileCollision(GameEntity e) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile t = getTile(x, y);
                if (!t.isFloor())
                    e.handleCollisionWith(t.getBox());
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract void spawnEntities();

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getRoomId() {
        return roomId;
    }
}
