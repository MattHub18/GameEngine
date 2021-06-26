package com.company.hud;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.awt.*;

public class Bar implements Graphic {

    protected static final int borderSize = 2;
    private final DrawableComponent component;
    private int offX;
    private int offY;
    private final int width;
    private final int height;
    private final int color;
    private final String text;
    private Rectangle bar;
    private Rectangle darkBar;
    private int lineSeparator;
    private int currentValue;
    private int maxValue;

    public Bar(int offX, int offY, int width, int height, int color, int maxValue, String text) {
        this.color = color;
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.text = text;
        this.bar = new Rectangle(offX, offY, width, height, true);
        this.darkBar = new Rectangle(offX, offY, 0, height, true);
        lineSeparator = offX;
        currentValue = maxValue;
        component = new DrawableComponent(offX, offY);
    }

    @Override
    public void update(GameLoop gl, float dt) {
        Point p = component.updatePosition();
        offX = p.x;
        offY = p.y;


        float damagePercent = 1 - (float) currentValue / (float) maxValue;
        float damage = damagePercent * width;
        lineSeparator = offX + (int) damage;

        if (lineSeparator < offX)
            lineSeparator = offX;
        else if (lineSeparator > offX + width)
            lineSeparator = offX + width;

        int blackWidth = lineSeparator - offX;
        if (blackWidth > width)
            blackWidth = width;

        int barWidth = width - blackWidth;
        darkBar = new Rectangle(offX, offY, width, height, true);
        bar = new Rectangle(lineSeparator, offY, barWidth, height, true);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addRectangle(darkBar, 0xff555555);
        r.addRectangle(bar, color);
        r.addThickRectangle(offX - borderSize, offY - borderSize, width + 2 * borderSize, height + 2 * borderSize, 0xff000000, borderSize);
        r.addFont(new Font("res/font/fps.png"), text, offX + width + 4, offY + 3, 0xffffffff);
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
