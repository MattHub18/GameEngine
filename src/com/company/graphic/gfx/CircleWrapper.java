package com.company.graphic.gfx;

import com.company.physics.primitives.Circle;

public class CircleWrapper {
    private final Circle circle;
    private final int color;
    private final boolean isFull;

    public CircleWrapper(Circle circle, int color, boolean isFull) {
        this.circle = circle;
        this.color = color;
        this.isFull = isFull;
    }

    public Circle getCircle() {
        return circle;
    }

    public int getColor() {
        return color;
    }

    public boolean isFull() {
        return isFull;
    }
}