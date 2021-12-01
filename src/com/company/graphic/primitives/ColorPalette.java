package com.company.graphic.primitives;

import com.company.graphic.gfx.Color;

public abstract class ColorPalette {
    public static final int WHITE = new Color(0xffffffff).getValue();
    public static final int BLACK = new Color(0xff000000).getValue();

    public static final int RED = new Color(0xffff0000).getValue();
    public static final int GREEN = new Color(0xff00ff00).getValue();
    public static final int BLUE = new Color(0xff0000ff).getValue();

    public static final int GREY = new Color(0xff6b6b6b).getValue();

    public static final int YELLOW = new Color(0xffffff00).getValue();

    public static final int INVISIBLE = new Color(0x00000000).getValue();
}
