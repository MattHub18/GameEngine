package com.company.worlds;

import com.company.entities.GameEntity;
import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.Camera;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.Resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public abstract class Map implements Graphic, Serializable {
    protected final int width;
    protected final int height;

    private final int WIDTH_IN_PIXEL;
    private final int HEIGHT_IN_PIXEL;

    private final byte mapId;
    private final Room[] rooms;
    protected byte roomId;

    private final int TILE_WIDTH = GameLoop.TILE_WIDTH;
    private final int TILE_HEIGHT = GameLoop.TILE_HEIGHT;

    protected Entity player;

    protected java.util.Map<Byte, List<Entity>> enemies = new HashMap<>();

    public Map(byte id, byte startRoom, Room[] r, Entity player) {
        this.mapId = id;
        this.rooms = r;
        this.roomId = startRoom;
        this.width = r[startRoom].tiles[0].length;
        this.height = r[startRoom].tiles.length;

        WIDTH_IN_PIXEL = width * TILE_WIDTH;
        HEIGHT_IN_PIXEL = height * TILE_HEIGHT;

        this.player = player;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        checkPlayerPosition();
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage tile = new TileImage(Resources.TEXTURES.get(mapId), TILE_WIDTH, TILE_HEIGHT);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                r.addImage(tile.getTile(rooms[roomId].tiles[y][x], roomId), x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
    }

    public int getWidthInPixel() {
        return WIDTH_IN_PIXEL;
    }

    public int getHeightInPixel() {
        return HEIGHT_IN_PIXEL;
    }

    public void registerWorldToCamera(Camera camera) {
        camera.setMap(this);
    }

    public void collisions(GameEntity tmp) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tmp.handleCollisionWith(rooms[roomId].getTile(x, y));
    }

    public Tile getTile(int x, int y) {
        return rooms[roomId].getTile(x, y);
    }

    protected abstract void checkPlayerPosition();

    public byte getCurrentRoomId() {
        return roomId;
    }

    public List<Entity> getEnemies(byte id) {
        return enemies.get(id);
    }

    public int getNumberOfRooms() {
        return rooms.length;
    }
}
