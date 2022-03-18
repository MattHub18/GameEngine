package com.company.graphic.primitives;

import com.company.entities.human.entity.GameEntity;
import com.company.observer.Observer;
import com.company.observer.Subject;
import com.company.physics.basics.Point;

import java.io.Serializable;

public class Camera implements Observer, Serializable {
    private int viewportSizeX;
    private int viewportSizeY;
    private int camX;
    private int camY;

    private int widthInPixel;
    private int heightInPixel;

    private int posX;
    private int posY;

    public void centerCamera() {
        viewportSizeX = Window.WIDTH;
        viewportSizeY = Window.HEIGHT;
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
        if (subject != null)
            subject.removeObserver("camera");
    }

    @Override
    public <T> void updateValue(T value) {
        Point p = (Point) value;
        posX = p.getX();
        posY = p.getY();
    }
}
