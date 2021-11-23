package com.company.graphic.wrappers;

import com.company.graphic.gfx.Image;

public class ImageWrapper {
    private final Image image;
    private final int offX;
    private final int offY;

    public ImageWrapper(Image image, int offX, int offY) {
        this.image = image;
        this.offX = offX;
        this.offY = offY;
    }

    public Image getImage() {
        return image;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }
}
