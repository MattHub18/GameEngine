package com.company.graphic.primitives;

import com.company.entities.Entity;
import com.company.worlds.Map;

public class Camera {
    private Entity entityCameraCentered;
    private final int viewportSizeX;
    private final int viewportSizeY;
    private int camX;
    private int camY;

    public Camera() {
        viewportSizeX = GameLoop.WIDTH;
        viewportSizeY = GameLoop.HEIGHT;
    }

    public void centerCamera() {
        int offsetMaxX = Map.WIDTH_IN_PIXEL - viewportSizeX;
        int offsetMaxY = Map.HEIGHT_IN_PIXEL - viewportSizeY;
        int offsetMinX = 0;
        int offsetMinY = 0;

        camX = entityCameraCentered.getPosX() - viewportSizeX / 2;
        camY = entityCameraCentered.getPosY() - viewportSizeY / 2;

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
        this.entityCameraCentered = entity;
    }
}
