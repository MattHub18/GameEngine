package com.company.resources;

public abstract class SystemConstants {
    public static int TILE_WIDTH = 0;
    public static int TILE_HEIGHT = 0;

    public static void register(int w, int h) {
        TILE_WIDTH = w;
        TILE_HEIGHT = h;
    }
}
