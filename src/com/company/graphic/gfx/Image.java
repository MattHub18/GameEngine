package com.company.graphic.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private boolean alpha = false;
    private int[] pixels;

    private int lightBlock = Light.NONE;

    public Image(String path) {
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
    }

    public Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;

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

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public int getLightBlock() {
        return lightBlock;
    }

    public void setLightBlock(int lightBlock) {
        this.lightBlock = lightBlock;
    }
}
