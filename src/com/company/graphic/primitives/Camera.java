package com.company.graphic.primitives;

import com.company.entities.Entity;
import com.company.worlds.Map;

public class Camera {
    private Entity entityRegistered;
    private Map mapRegistered;
    private final int viewportSizeX;
    private final int viewportSizeY;
    private int camX;
    private int camY;

    public Camera(Entity entity, Map map) {
        this.entityRegistered = entity;
        this.mapRegistered = map;
        viewportSizeX = GameLoop.WIDTH;
        viewportSizeY = GameLoop.HEIGHT;
    }

    public void centerCamera() {
        int offsetMaxX = mapRegistered.getWidthInPixel() - viewportSizeX;
        int offsetMaxY = mapRegistered.getHeightInPixel() - viewportSizeY;
        int offsetMinX = 0;
        int offsetMinY = 0;

        camX = entityRegistered.getPosX() - viewportSizeX / 2;
        camY = entityRegistered.getPosY() - viewportSizeY / 2;

        if (camX > offsetMaxX)
            camX = offsetMaxX;
        else if (camX < offsetMinX)
            camX = offsetMinX;
        if (camY > offsetMaxY)
            camY = offsetMaxY;
        else if (camY < offsetMinY)
            camY = offsetMinY;
    }

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }

    public int getMaxViewX() {
        return viewportSizeX + camX;
    }

    public int getMaxViewY() {
        return viewportSizeY + camY;
    }

    public void setEntity(Entity entity) {
        this.entityRegistered = entity;
    }

    public void setMap(Map map) {
        this.mapRegistered = map;
    }

    public int getMapWidthInPixel() {
        return mapRegistered.getWidthInPixel();
    }

    public int getMapHeightInPixel() {
        return mapRegistered.getHeightInPixel();
    }
}
