package com.company.hud;

import com.company.graphic.primitives.GameLoop;

import java.awt.*;

public class DrawableComponent {
    private int gapX;
    private int gapY;
    private int offX;
    private int offY;

    public DrawableComponent(int offX, int offY) {
        this.offX = offX;
        this.offY = offY;
        gapX = GameLoop.WIDTH - offX;
        gapY = GameLoop.HEIGHT - offY;
    }

    public Point updatePosition() {
        updateGap();
        if (GameLoop.WIDTH > gapX)
            offX = GameLoop.WIDTH - gapX;
        if (GameLoop.HEIGHT > gapY)
            offY = GameLoop.HEIGHT - gapY;
        return new Point(offX, offY);
    }

    private void updateGap() {
        if (gapX < 0)
            gapX = GameLoop.WIDTH - offX;
        if (gapY < 0)
            gapY = GameLoop.HEIGHT - offY;
    }
}

