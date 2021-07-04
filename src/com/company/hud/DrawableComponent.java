package com.company.hud;

import com.company.graphic.primitives.Window;

import java.awt.*;

public class DrawableComponent {
    private int gapX;
    private int gapY;
    private int offX;
    private int offY;

    public DrawableComponent(int offX, int offY) {
        this.offX = offX;
        this.offY = offY;
        gapX = Window.WIDTH - offX;
        gapY = Window.HEIGHT - offY;
    }

    public Point updatePosition() {
        updateGap();
        if (Window.WIDTH > gapX)
            offX = Window.WIDTH - gapX;
        if (Window.HEIGHT > gapY)
            offY = Window.HEIGHT - gapY;
        return new Point(offX, offY);
    }

    private void updateGap() {
        if (gapX < 0)
            gapX = Window.WIDTH - offX;
        if (gapY < 0)
            gapY = Window.HEIGHT - offY;
    }
}
