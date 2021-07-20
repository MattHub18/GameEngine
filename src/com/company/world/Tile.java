package com.company.world;

import com.company.entities.GameEntity;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;
import com.company.resources.Resources;

public class Tile implements GameEntity {
    private final byte tileId;
    private final Rectangle box;

    public Tile(byte tileId, int x, int y) {
        this.tileId = tileId;
        int posX = x * GameLoop.TILE_WIDTH;
        int posY = y * GameLoop.TILE_HEIGHT;
        box = new Rectangle(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT), 0xff000000, false);
    }

    public Rectangle getBox() {
        return box;
    }

    @Override
    public void handleCollisionWith(GameEntity e) {
    }

    public boolean isFloor() {
        return tileId == Resources.FLOOR;
    }
}
