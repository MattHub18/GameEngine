package com.company.world;

import com.company.graphic.gfx.Rectangle;
import com.company.physics.basics.Vector;

import static com.company.resources.AbstractConstants.TILE_HEIGHT;
import static com.company.resources.AbstractConstants.TILE_WIDTH;

public class Tile {
    public static final byte FLOOR = 0;
    private final byte tileId;
    private final Rectangle box;

    public Tile(byte tileId, int x, int y) {
        this.tileId = tileId;
        int posX = x * TILE_WIDTH();
        int posY = y * TILE_HEIGHT();
        box = new Rectangle(new Vector(posX, posY), new Vector(posX + TILE_WIDTH(), posY + TILE_HEIGHT()));
    }

    public Rectangle getBox() {
        return box;
    }

    public boolean isFloor() {
        return tileId == FLOOR;
    }
}
