package com.company.graphic.gfx;

public class RectangleWrapper {
    private final Rectangle rectangle;
    private final int color;

    public RectangleWrapper(Rectangle rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getColor() {
        return color;
    }

    public boolean isFull() {
        return rectangle.isFull();
    }
}
