package com.company.graphic.gfx;

public class Rectangle {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final boolean isFull;
    private final int color;
    private final boolean movable;

    public Rectangle(int x, int y, int width, int height, boolean isFull, int color, boolean movable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isFull = isFull;
        this.color = color;
        this.movable = movable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isFull() {
        return isFull;
    }

    public int getColor() {
        return color;
    }

    public boolean isMovable() {
        return movable;
    }

}
