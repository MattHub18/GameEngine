package com.company.directions;

public abstract class SystemFacingDirections {
    private static byte SOUTH = 0;
    private static byte WEST = 0;
    private static byte NORTH = 0;
    private static byte EAST = 0;

    private static byte TOTAL_DIRECTION = 0;

    public SystemFacingDirections() {
        SOUTH = giveSouth();
        WEST = giveWest();
        NORTH = giveNorth();
        EAST = giveEast();
        TOTAL_DIRECTION = giveTotalDirection();
    }

    public static byte SOUTH() {
        return SOUTH;
    }

    public static byte WEST() {
        return WEST;
    }

    public static byte NORTH() {
        return NORTH;
    }

    public static byte EAST() {
        return EAST;
    }

    public static byte TOTAL_DIRECTION() {
        return TOTAL_DIRECTION;
    }

    public abstract byte giveSouth();

    public abstract byte giveWest();

    public abstract byte giveNorth();

    public abstract byte giveEast();

    public abstract byte giveTotalDirection();
}
