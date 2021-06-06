package com.company.graphic.gfx;

public class FontWrapper {
    private final Font font;
    private final String text;
    private final int offX;
    private final int offY;
    private final int color;

    public FontWrapper(Font font, String text, int offX, int offY, int color) {
        this.font = font;
        this.text = text;
        this.offX = offX;
        this.offY = offY;
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return offX;
    }

    public int getY() {
        return offY;
    }

    public int getColor() {
        return color;
    }
}
