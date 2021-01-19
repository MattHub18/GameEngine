package com.company.graphic.major;

import com.company.graphic.GameLoop;
import com.company.graphic.gfx.Animation;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Image;
import com.company.graphic.gfx.ImageWrapper;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Comparator;

public class Render {
    private int width;
    private int height;
    private int[] pixels;

    private final int[] depths;
    private int zDepth = 0;

    private final ArrayList<ImageWrapper> images;
    private boolean processing = false;

    public Render(GameLoop gl) {
        width = GameLoop.WIDTH;
        height = GameLoop.HEIGHT;
        pixels = ((DataBufferInt) gl.getWindow().getImage().getRaster().getDataBuffer()).getData();

        depths = new int[pixels.length];

        images = new ArrayList<>();
    }

    public void process() {
        processing = true;
        images.sort(Comparator.comparingInt(ImageWrapper::getDepth));

        for (ImageWrapper img : images) {
            zDepth = img.getDepth();
            drawImage(img.getImage(), img.getOffX(), img.getOffY());
        }

        images.clear();
        processing = false;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            depths[i] = 0;
        }
    }

    private void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;

        if ((x < 0 || x >= width || y < 0 || y >= height) || alpha == 0)
            return;

        int index = x + y * width;

        if (depths[index] > zDepth)//TODO true solo se printo in ordine inverso di chiamata
            return;

        depths[index] = zDepth;

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

    public void drawImage(Image image, int offX, int offY) {
        if (image.isAlpha() && !processing) {
            images.add(new ImageWrapper(image, zDepth, offX, offY));
            return;
        }

        if (offX < -image.getWidth() || offX >= width)
            return;
        if (offY < -image.getHeight() || offY >= height)
            return;

        int startX = 0;
        int startY = 0;
        int width = image.getWidth();
        int height = image.getHeight();


        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= this.width)
            width -= (width + offX - this.width);
        if (height + offY >= this.height)
            height -= (height + offY - this.height);

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
            }
        }
    }

    public void drawAnimation(Animation image, int offX, int offY, int tileX, int tileY) {

        if (image.isAlpha() && !processing) {
            images.add(new ImageWrapper(image.getAnimation(tileX, tileY), zDepth, offX, offY));
            return;
        }

        if (offX < -image.getWidth() || offX >= this.width)
            return;
        if (offY < -image.getHeight() || offY >= this.height)
            return;

        int startX = 0;
        int startY = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= this.width)
            width -= (width + offX - this.width);
        if (height + offY >= this.height)
            height -= (height + offY - this.height);

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getWidth()) + (y + tileY * image.getHeight()) * image.getWidth()]);
            }
        }
    }

    public void drawFont(Font font, String text, int offX, int offY, int color) {
        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);
            for (int y = 0; y < font.getFontImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
                        setPixel(x + offset + offX, y + offY, color);
                    }

                }
            }
            offset += font.getWidths()[unicode];
        }
    }
}
