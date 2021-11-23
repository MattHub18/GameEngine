package com.company.hud;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.observer.Observer;
import com.company.observer.Subject;
import com.company.physics.basics.Vector;

import java.io.Serializable;

public class Bar implements Graphic, Observer, Serializable {

    private final int offX;
    private final int offY;
    private final int width;
    private final int height;
    private final int borderSize;
    private final int maxValue;
    private int color;
    private final String text;

    private Rectangle bar;
    private Rectangle darkBar;
    private int darkColor;
    private int borderColor;
    private int fontColor;
    private int lineSeparator;
    private int currentValue;


    public Bar(int offX, int offY, int width, int height, int color, int borderSize, int maxValue, String text, Subject subject) {
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderSize = borderSize;
        this.maxValue = maxValue;
        this.text = text;

        darkColor = 0xff555555;
        borderColor = 0xff000000;
        fontColor = 0xffffffff;

        this.bar = new Rectangle(new Vector(offX, offY), new Vector(offX + width, offY + height), color, true);
        this.darkBar = new Rectangle(new Vector(offX, offY), new Vector(offX, offY + height), darkColor, true);

        lineSeparator = offX;
        currentValue = maxValue;
        registerEntityToObserver(subject);
    }

    @Override
    public void update(GameLoop gl, float dt) {

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
        r.addThickRectangle(offX - borderSize, offY - borderSize, width + 2 * borderSize, height + 2 * borderSize, borderColor, borderSize);
        r.addFont(new Font("res/font/fps.png", text, offX + width + 4, offY + 3, fontColor));
    }

    @Override
    public void registerEntityToObserver(Subject subject) {
        subject.addObserver(text, this);
    }

    @Override
    public void unregisterEntityToObserver(Subject subject) {
        subject.removeObserver(text);
        zeroBar();
    }

    @Override
    public <T> void updateValue(T value) {
        currentValue = (int) value;
    }

    private void zeroBar() {
        color = 0x00000000;
        darkColor = 0x00000000;
        borderColor = 0x00000000;
        fontColor = 0x00000000;
    }
}
