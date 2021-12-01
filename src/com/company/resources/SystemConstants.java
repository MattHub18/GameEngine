package com.company.resources;

public abstract class SystemConstants {
    private static int TILE_WIDTH = 0;
    private static int TILE_HEIGHT = 0;

    public SystemConstants() {
        TILE_WIDTH = giveWidth();
        TILE_HEIGHT = giveHeight();
    }

    public static int TILE_WIDTH() {
        return TILE_WIDTH;
    }

    public static int TILE_HEIGHT() {
        return TILE_HEIGHT;
    }

    public abstract int giveWidth();

    public abstract int giveHeight();
}