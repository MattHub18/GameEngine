package com.company.world;

import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;
import com.company.resources.SystemConstants;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Tile {
    private final byte tileId;
    private final AxisAlignedBoundingBox box;

    public Tile(byte tileId, int x, int y) {
        this.tileId = tileId;
        int posX = x * TILE_WIDTH;
        int posY = y * TILE_HEIGHT;
        box = new AxisAlignedBoundingBox(new Vector(posX, posY), new Vector(posX + TILE_WIDTH, posY + TILE_HEIGHT));
    }

    public AxisAlignedBoundingBox getBox() {
        return box;
    }

    public boolean isFloor() {
        return SystemConstants.FLOOR.contains(tileId);
    }
}
