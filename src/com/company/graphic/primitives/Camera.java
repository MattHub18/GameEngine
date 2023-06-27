package com.company.graphic.primitives;

import com.company.entities.entity.GameEntity;
import com.company.observer.Observer;
import com.company.observer.Subject;
import com.company.physics.basics.Point;

public class Camera implements Observer {

    private final Window window;
    private int viewportSizeX;
    private int viewportSizeY;
    private int camX;
    private int camY;
    private int widthInPixel;
    private int heightInPixel;
    private int posX;
    private int posY;

    public Camera(Window window) {
        this.window = window;
    }

    public void centerCamera() {
        viewportSizeX = window.getWidth();
        viewportSizeY = window.getHeight();
        int offsetMaxX = widthInPixel - viewportSizeX;
        int offsetMaxY = heightInPixel - viewportSizeY;
        int offsetMinX = 0;
        int offsetMinY = 0;

        camX = posX - viewportSizeX / 2;
        camY = posY - viewportSizeY / 2;

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

    public int getWidthInPixel() {
        return widthInPixel;
    }

    public int getHeightInPixel() {
        return heightInPixel;
    }

    public void updateCamera(RenderObject obj) {
        widthInPixel = obj.getWidthInPixel();
        heightInPixel = obj.getHeightInPixel();
    }

    @Override
    public void registerEntityToObserver(Subject subject) {
        if (subject != null) {
            subject.addObserver("camera", this);
            posX = ((GameEntity) subject).getPosX();
            posY = ((GameEntity) subject).getPosY();
        }
    }

    @Override
    public void unregisterEntityToObserver(Subject subject) {
        if (subject != null) {
            subject.removeObserver("camera");
            posX = 0;
            posY = 0;
        }
    }

    @Override
    public void updateValue(Object value) {
        Point p = (Point) value;
        posX = p.getX();
        posY = p.getY();
    }
}
