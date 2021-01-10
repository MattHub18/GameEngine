package com.company.graphic.major;

import com.company.graphic.GameLoop;

import java.awt.image.DataBufferInt;

public class Render {
    private int width;
    private int height;
    private int[] pixels;

    public Render(GameLoop gl) {
        width = GameLoop.WIDTH;
        height = GameLoop.HEIGHT;
        pixels = ((DataBufferInt) gl.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;

        if ((x < 0 || x >= width || y < 0 || y >= height) || alpha == 0)
            return;

        int index = x + y * width;

        if (alpha == 255)
            pixels[index] = value;
        else {
            int pixelColor = pixels[index];
            int red = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int green = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int blue = ((pixelColor) & 0xff) - (int) ((((pixelColor) & 0xff) - ((value) & 0xff)) * (alpha / 255f));
            pixels[index] = (red << 16 | green << 8 | blue);
        }
    }

    public void drawBorderRectangle(int offX, int offY, int width, int height, int color) {

        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = 0; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }

    }

    public void drawFullRectangle(int offX, int offY, int width, int height, int color) {

        if (offX < -width || offX >= width)
            return;
        if (offY < -height || offY >= this.height)
            return;

        int startX = 0;
        int startY = 0;
        int newWidth = width;
        int newHeight = height;


        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= this.width)
            newWidth -= (newWidth + offX - this.width);
        if (height + offY >= this.height)
            newHeight -= (newHeight + offY - this.height);

        for (int y = startY; y < newHeight; y++) {
            for (int x = startX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }

    }
}
