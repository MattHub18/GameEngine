package com.company.graphic.primitives;

public abstract class ColorPalette {
    public static final int WHITE = 0xffffffff;
    public static final int BLACK = 0xff000000;

    public static final int RED = 0xffff0000;
    public static final int GREEN = 0xff00ff00;
    public static final int BLUE = 0xff0000ff;

    public static final int GREY = 0xff6b6b6b;

    public static final int YELLOW = 0xffffff00;

    public static final int INVISIBLE = 0x00000000;

    public static int FPS = BLACK;

    public static void setPrimary(int color) {
        FPS = color;
    }
}
