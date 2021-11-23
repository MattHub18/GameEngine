package com.company.graphic.primitives;

import com.company.entities.human.GameEntity;
import com.company.world.World;

public class Camera {
    private final GameEntity entityRegistered;
    private final World worldRegistered;
    private int viewportSizeX;
    private int viewportSizeY;
    private int camX;
    private int camY;

    public Camera(World world) {
        this.worldRegistered = world;
        this.entityRegistered = worldRegistered.getPlayer();
    }

    public void centerCamera() {
        viewportSizeX = Window.WIDTH;
        viewportSizeY = Window.HEIGHT;
        int offsetMaxX = worldRegistered.getWidthInPixel() - viewportSizeX;
        int offsetMaxY = worldRegistered.getHeightInPixel() - viewportSizeY;
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

    public int getMapWidthInPixel() {
        return worldRegistered.getWidthInPixel();
    }

    public int getMapHeightInPixel() {
        return worldRegistered.getHeightInPixel();
    }
}
