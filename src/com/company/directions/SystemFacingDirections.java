package com.company.directions;

public abstract class SystemFacingDirections {
    public static byte SOUTH = -1;
    public static byte WEST = -1;
    public static byte NORTH = -1;
    public static byte EAST = -1;

    public static void register(byte s, byte w, byte n, byte e) {
        SOUTH = s;
        WEST = w;
        NORTH = n;
        EAST = e;
    }
}
