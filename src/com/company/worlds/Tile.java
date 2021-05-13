package com.company.worlds;

import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.resources.Resources;

public class Tile {
    private final byte tileId;
    private final AxisAlignedBoundingBox box;


    public Tile(byte tileId, int x, int y) {
        this.tileId = tileId;
        int posX = x * GameLoop.TILE_WIDTH;
        int posY = y * GameLoop.TILE_HEIGHT;
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT));
    }

    public AxisAlignedBoundingBox getBox() {
        return box;
    }

    public boolean isFloor() {
        return tileId == Resources.FLOOR;
    }
}
