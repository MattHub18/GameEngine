package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Circle;
import com.company.graphic.primitives.CameraShift;

import java.util.ArrayList;

public class CircleRender implements RenderInterface {

    private final ArrayList<Circle> circles;
    private final BasicRender basicRender;

    public CircleRender(BasicRender basicRender) {
        this.basicRender = basicRender;
        this.circles = new ArrayList<>();
    }

    @Override
    public void process() {
        for (Circle cir : circles) {
            drawCircumference(cir, cir.getColor(), cir.isFull());
        }
    }

    @Override
    public void clear() {
        circles.clear();
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
    }

    private void drawCircumference(Circle circle, int color, boolean isFull) {
        int xc = (int) circle.getCenter().getX();
        int yc = (int) circle.getCenter().getY();
        int r = circle.getRadius();

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        if (!isFull)
            drawCircumferencePixel(xc, yc, x, y, color, circle.isMovable());
        else
            drawCircleLine(xc, yc, x, y, color, circle.isMovable());
        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;

            if (!isFull)
                drawCircumferencePixel(xc, yc, x, y, color, circle.isMovable());
            else
                drawCircleLine(xc, yc, x, y, color, circle.isMovable());
        }
    }

    private void drawCircumferencePixel(int xc, int yc, int x, int y, int color, boolean movable) {
        CameraShift structure = basicRender.cameraShift(xc - x, yc - y, xc + x, yc + y, movable);

        if (structure == null)
            return;

        int camX = structure.getCamX();
        int camY = structure.getCamY();

        basicRender.setPixel(xc + x - camX, yc + y - camY, color);
        basicRender.setPixel(xc - x - camX, yc + y - camY, color);
        basicRender.setPixel(xc + x - camX, yc - y - camY, color);
        basicRender.setPixel(xc - x - camX, yc - y - camY, color);
        basicRender.setPixel(xc + y - camX, yc + x - camY, color);
        basicRender.setPixel(xc - y - camX, yc + x - camY, color);
        basicRender.setPixel(xc + y - camX, yc - x - camY, color);
        basicRender.setPixel(xc - y - camX, yc - x - camY, color);
    }

    private void drawCircleLine(int xc, int yc, int x, int y, int color, boolean movable) {
        drawLine(xc - x, yc + y, xc + x, yc + y, color, movable);
        drawLine(xc - x, yc - y, xc + x, yc - y, color, movable);
        drawLine(xc - y, yc + x, xc + y, yc + x, color, movable);
        drawLine(xc - y, yc - x, xc + y, yc - x, color, movable);
    }

    private void drawLine(int x0, int y0, int xf, int yf, int color, boolean movable) {

        if (y0 > yf) {
            y0 = y0 + yf;
            yf = y0 - yf;
            y0 = y0 - yf;
        }

        if (x0 > xf) {
            x0 = x0 + xf;
            xf = x0 - xf;
            x0 = x0 - xf;
        }

        CameraShift structure = basicRender.cameraShift(x0, y0, xf, yf, movable);

        int startX = structure.getStartX();
        int startY = structure.getStartY();
        int width = structure.getWidth();
        int height = structure.getHeight();
        int camX = structure.getCamX();
        int camY = structure.getCamY();

        for (int y = startY; y <= height; y++) {
            for (int x = startX; x <= width; x++) {
                basicRender.setPixel(x - camX, y - camY, color);
            }
        }
    }
}
