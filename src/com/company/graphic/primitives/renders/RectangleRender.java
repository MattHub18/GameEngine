package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.Window;
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
        for (Rectangle rec : rectangles) {
            if (rec.isFull())
                drawFullRectangle(rec);
            else
                drawBorderRectangle(rec, rec.getColor());
        }
    }

    @Override
    public void clear() {
        rectangles.clear();
    }

    public void addRectangle(Rectangle rect) {
        rectangles.add(rect);
    }

    public void addThickRectangle(int smallestOffX, int smallestOffY, int biggestWidth, int biggestHeight, int color, int thickness) {
        for (int i = 0; i < thickness; i++) {
            addRectangle(new Rectangle(new Vector(smallestOffX + i, smallestOffY + i), new Vector(smallestOffX + biggestWidth - i, smallestOffY + biggestHeight - i), color, false));
        }
    }

    private void drawBorderRectangle(Rectangle rect, int color) {

        int offX = (int) rect.getStartX();
        int offY = (int) rect.getStartY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        if (basicRender.outOfBounds(offX, -width, Window.WIDTH, offY, -height, Window.HEIGHT))
            return;

        int startX = 0;
        int startY = 0;

        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= Window.WIDTH)
            width -= (width + offX - Window.WIDTH);
        if (height + offY >= Window.HEIGHT)
            height -= (height + offY - Window.HEIGHT);

        for (int y = startY; y <= height; y++) {
            basicRender.setPixel(offX, y + offY, color);
            basicRender.setPixel(offX + width, y + offY, color);
        }

        for (int x = startX; x <= width; x++) {
            basicRender.setPixel(x + offX, offY, color);
            basicRender.setPixel(x + offX, offY + height, color);
        }
    }

    private void drawFullRectangle(Rectangle rect) {

        int offX = (int) rect.getStartX();
        int offY = (int) rect.getStartY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        if (width < 0 || height < 0)
            return;

        drawFullRectangle(new Rectangle(new Vector(offX + 1, offY + 1), new Vector(offX + width - 1, offY + height - 1), rect.getColor(), true));

        drawBorderRectangle(rect, rect.getColor());
    }
}
