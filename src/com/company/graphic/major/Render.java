package com.company.graphic.major;

import com.company.graphic.GameLoop;
import com.company.graphic.gfx.*;

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
    private final ArrayList<LightWrapper> lights;

    private final int[] lightPixels;
    private final int[] lightBlock;

    private boolean processing = false;

    public Render(GameLoop gl) {
        width = GameLoop.WIDTH;
        height = GameLoop.HEIGHT;
        pixels = ((DataBufferInt) gl.getWindow().getImage().getRaster().getDataBuffer()).getData();

        depths = new int[pixels.length];

        images = new ArrayList<>();
        lights = new ArrayList<>();

        lightPixels = new int[pixels.length];
        lightBlock = new int[pixels.length];
    }

    public void process() {
        processing = true;
        images.sort(Comparator.comparingInt(ImageWrapper::getDepth));

        for (ImageWrapper img : images) {
            zDepth = img.getDepth();
            drawImage(img.getImage(), img.getOffX(), img.getOffY());
        }

        for (LightWrapper lgh : lights) {
            drawLightRequest(lgh.getLight(), lgh.getX(), lgh.getY());
        }

        for (int i = 0; i < pixels.length; i++) {
            float red = ((lightPixels[i] >> 16) & 0xff) / 255f;
            float green = ((lightPixels[i] >> 8) & 0xff) / 255f;
            float blue = ((lightPixels[i]) & 0xff) / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int) (((pixels[i]) & 0xff) * blue));
        }

        images.clear();
        lights.clear();
        processing = false;
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            depths[i] = 0;
            lightPixels[i] = 0;
            lightBlock[i] = 0;
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
                setLightBlock(x + offX, y + offY, image.getLightBlock());
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
                setLightBlock(x + offX, y + offY, image.getLightBlock());
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

    public void drawLight(Light light, int offX, int offY) {
        lights.add(new LightWrapper(light, offX, offY));
    }

    private void drawLightRequest(Light light, int offX, int offY) {
        for (int i = 0; i <= light.getDiameter(); i++) {
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offX, offY);
        }
    }

    private void drawLightLine(Light light, int x0, int y0, int x1, int y1, int offX, int offY) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int errD = dx - dy;
        int err;

        while (true) {

            int screenX = x0 - light.getRadius() + offX;
            int screenY = y0 - light.getRadius() + offY;

            if (screenX < 0 || screenX >= width || screenY < 0 || screenY >= height)
                return;

            int lightColor = light.getLightValue(x0, y0);

            if (lightColor == 0)
                return;

            if (lightBlock[screenX + screenY * width] == Light.FULL)
                return;

            setLightMap(screenX, screenY, lightColor);

            if (x0 == x1 && y0 == y1)
                break;
            err = 2 * errD;
            if (err > -1 * dy) {
                errD -= dy;
                x0 += sx;
            }
            if (err < dx) {
                errD += dx;
                y0 += sy;
            }
        }
    }

    private void setLightMap(int x, int y, int value) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;

        int baseColor = lightPixels[x + y * width];
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        lightPixels[x + y * width] = (maxRed << 16 | maxGreen << 8 | maxBlue);

    }

    private void setLightBlock(int x, int y, int value) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;
        if (depths[x + y * width] > zDepth)
            return;
        lightBlock[x + y * width] = value;

    }
}
