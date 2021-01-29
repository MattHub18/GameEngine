package com.company.physics.basics;

public class Vector {
    private float x;
    private float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        x = v.x;
        y = v.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector sub(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector mul(float value) {
        this.x *= value;
        this.y *= value;
        return this;
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float dot(Vector v) {
        return x * v.x + y * v.y;
    }


}
