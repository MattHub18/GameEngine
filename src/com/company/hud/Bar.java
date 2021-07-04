package com.company.hud;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;

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
    private final int darkColor = 0xff555555;
    private int maxValue;
    private int currentValue;

    public Bar(int offX, int offY, int width, int height, int color, int maxValue, String text) {
        this.color = color;
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.text = text;
        this.bar = new Rectangle(new Vector(offX, offY), new Vector(offX + width, offY + height), color, true);
        this.darkBar = new Rectangle(new Vector(offX, offY), new Vector(offX, offY + height), darkColor, true);
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
        darkBar = new Rectangle(new Vector(offX, offY), new Vector(offX + width, offY + height), darkColor, true);
        bar = new Rectangle(new Vector(lineSeparator, offY), new Vector(lineSeparator + barWidth, offY + height), color, true);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addRectangle(darkBar);
        r.addRectangle(bar);
        r.addThickRectangle(offX - borderSize, offY - borderSize, width + 2 * borderSize, height + 2 * borderSize, 0xff000000, borderSize);
        r.addFont(new Font("res/font/fps.png", text, offX + width + 4, offY + 3, 0xffffffff));
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
