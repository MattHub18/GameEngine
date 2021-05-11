package com.company.worlds;

import java.io.Serializable;

public class Room implements Serializable {
    protected byte[][] tiles;

    public Room(byte[][] tiles) {
        this.tiles = tiles;
    }
}
