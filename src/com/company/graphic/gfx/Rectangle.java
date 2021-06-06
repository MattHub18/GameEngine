package com.company.graphic.gfx;

public class Rectangle {
    private final int startX;
    private final int startY;
    private final int width;
    private final int height;
    private final boolean isFull;

    public Rectangle(int startX, int startY, int width, int height, boolean isFull) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.isFull = isFull;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
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
}
