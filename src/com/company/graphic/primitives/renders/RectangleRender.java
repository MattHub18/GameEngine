package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.CameraShift;
import com.company.physics.basics.Vector;

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
                drawBorderRectangle(rect, (int) rect.getStartX(), (int) rect.getStartY(), rect.getColor());
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
            addRectangle(new Rectangle(new Vector(smallestOffX + i, smallestOffY + i), new Vector(smallestOffX + biggestWidth - i, smallestOffY + biggestHeight - i), color, false, movable));
        }
    }

    private void drawBorderRectangle(Rectangle rect, int offX, int offY, int color) {

        CameraShift structure = basicRender.cameraShift(offX, offY, (int) rect.getWidth(), (int) rect.getHeight(), rect.isMovable());

        int startX = structure.getStartX();
        int startY = structure.getStartY();
        int width = structure.getWidth();
        int height = structure.getHeight();
        int camX = structure.getCamX();
        int camY = structure.getCamY();

        for (int y = startY; y <= height; y++) {
            basicRender.setPixel(offX - camX, y + offY - camY, color);
            basicRender.setPixel(offX - camX + width, y + offY - camY, color);
        }

        for (int x = startX; x <= width; x++) {
            basicRender.setPixel(x + offX - camX, offY - camY, color);
            basicRender.setPixel(x + offX - camX, offY + height - camY, color);
        }
    }

    private void drawFullRectangle(Rectangle rect) {

        int offX = (int) rect.getStartX();
        int offY = (int) rect.getStartY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        if (width < 0 || height < 0)
            return;

        drawFullRectangle(new Rectangle(new Vector(offX + 1, offY + 1), new Vector(offX + width - 1, offY + height - 1), rect.getColor(), rect.isFull(), rect.isMovable()));

        drawBorderRectangle(rect, offX, offY, rect.getColor());
    }
}
