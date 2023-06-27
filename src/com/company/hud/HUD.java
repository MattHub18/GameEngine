package com.company.hud;

import com.company.graphic.Graphic;
import com.company.worlds.World;

public abstract class HUD implements Graphic {
    protected final World world;
    protected final int width;
    protected final int height;
    protected final int borderSize;

    public HUD(World world, int width, int height, int borderSize) {
        this.world = world;

        this.width = width;
        this.height = height;
        this.borderSize = borderSize;

        build();
    }

    protected abstract void build();
}
