package com.company.graphic.gfx;

public class Font {
    private final Image fontImage;
    private final int[] offsets;
    private final int[] widths;

    public Font(String path) {
        fontImage = new Image(path);
        offsets = new int[256];
        widths = new int[256];

        int unicode = 0;
        for (int i = 0; i < fontImage.getWidth(); i++) {
            if (fontImage.getPixels()[i] == 0xff0000ff)//start a new character
                offsets[unicode] = i;
            if (fontImage.getPixels()[i] == 0xffffff00) {//end a new character
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
}
