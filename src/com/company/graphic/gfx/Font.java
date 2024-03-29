package com.company.graphic.gfx;

import com.company.graphic.primitives.ColorPalette;

public class Font {
    private final Image fontImage;
    private final String text;
    private final int offX;
    private final int offY;
    private final int color;
    private final int[] offsets;
    private final int[] widths;

    public Font(String path, String text, int offX, int offY, int color, boolean movable) {
        fontImage = new Image(path, movable, true);
        this.text = text;
        this.offX = offX;
        this.offY = offY;
        this.color = color;
        offsets = new int[256];
        widths = new int[256];

        int unicode = 0;
        for (int i = 0; i < fontImage.getWidth(); i++) {
            if (fontImage.getPixels()[i] == ColorPalette.BLUE)//start a new character
                offsets[unicode] = i;
            if (fontImage.getPixels()[i] == ColorPalette.YELLOW) {//end a new character
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public String getText() {
        return text;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }

    public int getColor() {
        return color;
    }

    public boolean isMovable() {
        return fontImage.isMovable();
    }
}
