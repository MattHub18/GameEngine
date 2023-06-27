package com.company.resources;

import com.company.entities.directions.SystemFacingDirections;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SystemConstants {
    public static int TILE_WIDTH = 0;
    public static int TILE_HEIGHT = 0;
    public static float SCALE = 0;

    public static ArrayList<Byte> FLOOR = null;

    public static void register(int w, int h, float s) {
        TILE_WIDTH = w;
        TILE_HEIGHT = h;
        SCALE = s;
    }

    public static void setFloor(Byte... tiles) {
        FLOOR = new ArrayList<>(Arrays.asList(tiles));
    }

    public static void setDirections(byte south, byte west, byte north, byte east) {
        SystemFacingDirections.register(south, west, north, east);
    }
}
