package com.company.graphic.gfx;

public class Circle {
    private final int xCenter;
    private final int yCenter;
    private final int radius;
    private final int color;
    private final boolean isFull;
    private final boolean movable;

    public Circle(int xCenter, int yCenter, int radius, int color, boolean isFull, boolean movable) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
        this.color = color;
        this.isFull = isFull;
        this.movable = movable;
    }

    public int getXCenter() {
        return xCenter;
    }

    public int getYCenter() {
        return yCenter;
    }

    public int getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isMovable() {
        return movable;
    }
}
