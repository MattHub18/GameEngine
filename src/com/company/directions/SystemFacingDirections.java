package com.company.directions;

public abstract class SystemFacingDirections {
    public static byte SOUTH = -1;
    public static byte WEST = -1;
    public static byte NORTH = -1;
    public static byte EAST = -1;

    public static byte TOTAL_DIRECTION = -1;

    public SystemFacingDirections() {
        SOUTH = giveSouth();
        WEST = giveWest();
        NORTH = giveNorth();
        EAST = giveEast();
        TOTAL_DIRECTION = giveTotalDirection();
    }

    public abstract byte giveSouth();

    public abstract byte giveWest();

    public abstract byte giveNorth();

    public abstract byte giveEast();

    public abstract byte giveTotalDirection();
}
