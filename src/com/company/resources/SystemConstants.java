package com.company.resources;

public abstract class SystemConstants {
    public static int TILE_WIDTH = 0;
    public static int TILE_HEIGHT = 0;

    public SystemConstants() {
        TILE_WIDTH = giveWidth();
        TILE_HEIGHT = giveHeight();
    }

    public abstract int giveWidth();

    public abstract int giveHeight();
}
