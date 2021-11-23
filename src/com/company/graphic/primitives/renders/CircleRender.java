package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Circle;

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
            drawCircumferencePixel(xc, yc, x, y, color);
        else
            drawCircleLine(xc, yc, x, y, color);
        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;

            if (!isFull)
                drawCircumferencePixel(xc, yc, x, y, color);
            else
                drawCircleLine(xc, yc, x, y, color);
        }
    }

    private void drawCircumferencePixel(int xc, int yc, int x, int y, int color) {
        basicRender.setPixel(xc + x, yc + y, color);
        basicRender.setPixel(xc - x, yc + y, color);
        basicRender.setPixel(xc + x, yc - y, color);
        basicRender.setPixel(xc - x, yc - y, color);
        basicRender.setPixel(xc + y, yc + x, color);
        basicRender.setPixel(xc - y, yc + x, color);
        basicRender.setPixel(xc + y, yc - x, color);
        basicRender.setPixel(xc - y, yc - x, color);
    }

    private void drawCircleLine(int xc, int yc, int x, int y, int color) {
        drawLine(xc - x, yc + y, xc + x, yc + y, color);
        drawLine(xc - x, yc - y, xc + x, yc - y, color);
        drawLine(xc - y, yc + x, xc + y, yc + x, color);
        drawLine(xc - y, yc - x, xc + y, yc - x, color);
    }

    private void drawLine(int x0, int y0, int xf, int yf, int color) {

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

        for (int y = y0; y <= yf; y++) {
            for (int x = x0; x <= xf; x++) {
                basicRender.setPixel(x, y, color);
            }
        }
    }
}
