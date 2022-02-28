package com.company.hud;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.ColorPalette;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.observer.Observer;
import com.company.observer.Subject;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

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
        this(offX, offY, width, height, color, borderSize, maxValue, text);
        registerEntityToObserver(subject);
    }


    public Bar(int offX, int offY, int width, int height, int color, int borderSize, int maxValue, String text) {
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.borderSize = borderSize;
        this.maxValue = maxValue;
        this.text = text;

        darkColor = ColorPalette.GREY;
        borderColor = ColorPalette.BLACK;
        fontColor = ColorPalette.WHITE;

        this.bar = new Rectangle(offX, offY, width, height, true, color, true);
        this.darkBar = new Rectangle(offX, offY, 0, height, true, darkColor, true);

        lineSeparator = offX;
        currentValue = maxValue;
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
        darkBar = new Rectangle(offX, offY, width, height, true, darkColor, true);
        bar = new Rectangle(lineSeparator, offY, barWidth, height, true, color, true);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addRectangle(darkBar);
        r.addRectangle(bar);
        r.addThickRectangle(offX - borderSize, offY - borderSize, width + 2 * borderSize, height + 2 * borderSize, borderColor, borderSize, true);
        r.addFont(new Font(Archive.FONT.get(SystemResources.FPS_FONT), text, offX + width + 4, offY + 3, fontColor, true));
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
        color = ColorPalette.INVISIBLE;
        darkColor = ColorPalette.INVISIBLE;
        borderColor = ColorPalette.INVISIBLE;
        fontColor = ColorPalette.INVISIBLE;
    }
}
