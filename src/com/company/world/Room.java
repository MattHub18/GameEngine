package com.company.world;

import java.io.Serializable;

public class Room implements Serializable {
    protected byte[][] tiles;

    public Room(byte[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(int x, int y) {
        return new Tile(tiles[y][x], x, y);
    }
}
