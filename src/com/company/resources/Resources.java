package com.company.resources;

import java.util.ArrayList;

public abstract class Resources {
    public static final ArrayList<String> TEXTURES = new ArrayList<>();

    public static final byte PLAYER = 0;
    public static final byte MAP = 1;
    public static final byte BULLET = 2;

    public static final byte PLAYER_FRONT = 0;
    public static final byte PLAYER_LEFT = 1;
    public static final byte PLAYER_BACK = 2;
    public static final byte PLAYER_RIGHT = 3;

    public static final byte BULLET_FRONT = 0;
    public static final byte BULLET_LEFT = 1;
    public static final byte BULLET_BACK = 2;
    public static final byte BULLET_RIGHT = 3;

    public static final byte BORDER = 0;
    public static final byte FLOOR = 1;
}
