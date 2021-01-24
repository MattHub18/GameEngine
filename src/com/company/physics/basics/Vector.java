package com.company.physics.basics;

public class Vector {
    private int x;
    private int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        x = v.x;
        y = v.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector add(Vector v) {
        this.x = x + v.x;
        this.y = y + v.y;
        return this;
    }

    public Vector sub(Vector v) {
        this.x = x - v.x;
        this.y = y - v.y;
        return this;
    }

    public Vector mul(float value) {
        this.x = (int) (x * value);
        this.y = (int) (y * value);
        return this;
    }

}
