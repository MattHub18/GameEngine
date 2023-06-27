package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Rectangle;

import java.util.ArrayList;

public class RectangleRender implements RenderInterface {
    private final ArrayList<Rectangle> rectangles;
    private final BasicRender basicRender;

    public RectangleRender(BasicRender basicRender) {
        this.basicRender = basicRender;
        this.rectangles = new ArrayList<>();
    }

    @Override
    public void process() {
        for (Rectangle rect : rectangles) {
            if (rect.isFull())
                drawFullRectangle(rect);
            else
                drawBorderRectangle(rect, rect.getX(), rect.getY(), rect.getColor());
        }
    }

    @Override
    public void clear() {
        rectangles.clear();
    }

    public void addRectangle(Rectangle rect) {
        rectangles.add(rect);
    }

    public void addThickRectangle(int smallestOffX, int smallestOffY, int biggestWidth, int biggestHeight, int color, int thickness, boolean movable) {
        for (int i = 0; i < thickness; i++) {
            addRectangle(new Rectangle(smallestOffX + i, smallestOffY + i, biggestWidth - i, biggestHeight - i, false, color, movable));
        }
    }

    private void drawBorderRectangle(Rectangle rect, int offX, int offY, int color) {

        int[] structure = basicRender.cameraShift(offX, offY, rect.getWidth(), rect.getHeight(), rect.isMovable());

        int startX = structure[0];
        int startY = structure[1];
        int width = structure[2];
        int height = structure[3];
        int camX = structure[4];
        int camY = structure[5];

        for (int y = startY; y < height; y++) {
            basicRender.setPixel(offX - camX, y + offY - camY, color);
            basicRender.setPixel(offX - camX + width - 1, y + offY - camY, color);
        }

        for (int x = startX + 1; x < width - 1; x++) {
            basicRender.setPixel(x + offX - camX, offY - camY, color);
            basicRender.setPixel(x + offX - camX, offY + height - 1 - camY, color);
        }
    }

    private void drawFullRectangle(Rectangle rect) {

        int offX = rect.getX();
        int offY = rect.getY();
        int width = rect.getWidth();
        int height = rect.getHeight();

        if (width <= 0 || height <= 0)
            return;

        drawFullRectangle(new Rectangle(offX + 1, offY + 1, width - 2, height - 2, rect.isFull(), rect.getColor(), rect.isMovable()));

        drawBorderRectangle(rect, offX, offY, rect.getColor());
    }
}
