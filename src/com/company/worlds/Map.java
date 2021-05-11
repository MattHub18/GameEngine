package com.company.worlds;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.resources.Resources;

public abstract class Map implements Graphic {
    protected final int width;
    protected final int height;

    public static int WIDTH_IN_PIXEL;
    public static int HEIGHT_IN_PIXEL;

    private final byte mapId;
    private final Room[] rooms;
    protected byte roomId;

    private final int TILE_WIDTH = GameLoop.TILE_WIDTH;
    private final int TILE_HEIGHT = GameLoop.TILE_HEIGHT;

    public Map(byte id, byte startRoom, Room[] r) {
        this.mapId = id;
        this.rooms = r;
        this.roomId = startRoom;
        this.width = r[startRoom].tiles[0].length;
        this.height = r[startRoom].tiles.length;

        WIDTH_IN_PIXEL = width * TILE_WIDTH;
        HEIGHT_IN_PIXEL = height * TILE_HEIGHT;
    }

    @Override
    public void update(GameLoop gl, float dt) {

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

    public AxisAlignedBoundingBox getTile(int x, int y) {
        if (rooms[roomId].tiles[y / TILE_HEIGHT][x / TILE_WIDTH] != Resources.FLOOR && rooms[roomId].tiles[(y - 1) / TILE_HEIGHT + 1][(x - 1) / TILE_WIDTH + 1] != Resources.FLOOR) {
            if (x < y)
                return new AxisAlignedBoundingBox(new Vector(0, HEIGHT_IN_PIXEL), new Vector(x, y + TILE_HEIGHT));
            return new AxisAlignedBoundingBox(new Vector(WIDTH_IN_PIXEL, 0), new Vector(x + TILE_WIDTH, y));
        }

        if (rooms[roomId].tiles[y / TILE_HEIGHT][x / TILE_WIDTH] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(0, 0), new Vector(x, y));

        if (rooms[roomId].tiles[(y - 1) / TILE_HEIGHT + 1][(x - 1) / TILE_WIDTH + 1] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(x + TILE_WIDTH, y + TILE_HEIGHT), new Vector(WIDTH_IN_PIXEL, HEIGHT_IN_PIXEL));

        return null;
    }
}
