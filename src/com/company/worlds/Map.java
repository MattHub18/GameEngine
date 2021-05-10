package com.company.worlds;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.resources.Resources;

public abstract class Map implements Graphic {
    protected static final int TILE_SIZE = 64;
    protected final int width;
    protected final int height;

    public static int WIDTH_IN_PIXEL;
    public static int HEIGHT_IN_PIXEL;

    private final byte mapId;
    private final Room[] rooms;
    protected byte roomId;

    public Map(byte id, byte startRoom, Room[] r) {
        this.mapId = id;
        this.rooms = r;
        this.roomId = startRoom;
        this.width = r[startRoom].tiles[0].length;
        this.height = r[startRoom].tiles.length;

        WIDTH_IN_PIXEL = width * TILE_SIZE;
        HEIGHT_IN_PIXEL = height * TILE_SIZE;
    }

    @Override
    public void update(GameLoop gl, float dt) {

    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage tile = new TileImage(Resources.TEXTURES.get(mapId), TILE_SIZE, TILE_SIZE);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                r.addImage(tile.getTile(rooms[roomId].tiles[y][x], roomId), x * TILE_SIZE, y * TILE_SIZE);
            }
        }
    }

    public AxisAlignedBoundingBox getTile(int x, int y) {
        if (rooms[roomId].tiles[y / TILE_SIZE][x / TILE_SIZE] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(0, 0), new Vector(x, y));

        else if (rooms[roomId].tiles[(y - 1) / TILE_SIZE + 1][(x - 1) / TILE_SIZE + 1] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(x + TILE_SIZE, y + TILE_SIZE), new Vector(WIDTH_IN_PIXEL, HEIGHT_IN_PIXEL));

        else if (rooms[roomId].tiles[y / TILE_SIZE][(x - 1) / TILE_SIZE + 1] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(x + TILE_SIZE, 0), new Vector(WIDTH_IN_PIXEL, y));

        else if (rooms[roomId].tiles[(y - 1) / TILE_SIZE + 1][x / TILE_SIZE] != Resources.FLOOR)
            return new AxisAlignedBoundingBox(new Vector(0, y + TILE_SIZE), new Vector(x, HEIGHT_IN_PIXEL));

        return null;
    }
}
