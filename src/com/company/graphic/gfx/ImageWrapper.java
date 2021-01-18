package com.company.graphic.gfx;

public class ImageWrapper {
    private final Image image;
    private final int depth;
    private final int offX;
    private final int offY;

    public ImageWrapper(Image image, int depth, int offX, int offY) {
        this.image = image;
        this.depth = depth;
        this.offX = offX;
        this.offY = offY;
    }

    public Image getImage() {
        return image;
    }

    public int getDepth() {
        return depth;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }
}
