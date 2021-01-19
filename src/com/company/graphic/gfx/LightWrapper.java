package com.company.graphic.gfx;

public class LightWrapper {
    private Light light;
    private int x;
    private int y;

    public LightWrapper(Light light, int x, int y) {
        this.light = light;
        this.x = x;
        this.y = y;
    }

    public Light getLight() {
        return light;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
