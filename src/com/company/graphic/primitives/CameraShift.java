package com.company.graphic.primitives;

public class CameraShift {

    private final int startX;
    private final int startY;
    private final int width;
    private final int height;
    private final int camX;
    private final int camY;

    public CameraShift(int startX, int startY, int width, int height, int camX, int camY) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.camX = camX;
        this.camY = camY;
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

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
}
