package com.company.graphic.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private int[] pixels;
    private final boolean movable;
    private final boolean opaque;

    public Image(String path, boolean movable, boolean opaque) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
            image.flush();
        }

        this.movable = movable;
        this.opaque = opaque;
    }

    public Image(int[] pixels, int width, int height, boolean movable, boolean opaque) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        this.movable = movable;
        this.opaque = opaque;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public boolean isMovable() {
        return movable;
    }
}
