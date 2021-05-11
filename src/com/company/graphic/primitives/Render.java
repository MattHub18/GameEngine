package com.company.graphic.primitives;

import com.company.graphic.gfx.*;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Comparator;

public class Render {
    private final int WIDTH;
    private final int HEIGHT;
    private final int[] pixels;

    private final ArrayList<ImageWrapper> images;
    private final ArrayList<LightWrapper> lights;
    private final int[] lightPixels;
    private final boolean[] brightness;

    private int depth = 0;
    private final int[] depths;

    private final Camera camera;

    public Render(GameLoop gl) {
        WIDTH = GameLoop.WIDTH;
        HEIGHT = GameLoop.HEIGHT;
        pixels = ((DataBufferInt) gl.getWindow().getImage().getRaster().getDataBuffer()).getData();

        camera = gl.getCamera();

        images = new ArrayList<>();
        lights = new ArrayList<>();
        lightPixels = new int[pixels.length];
        depths = new int[pixels.length];
        brightness = new boolean[pixels.length];
    }

    public void process() {
        images.sort(Comparator.comparingInt(ImageWrapper::getDepth));

        for (ImageWrapper img : images) {
            depth = img.getDepth();
            drawImage(img.getImage(), img.getOffX(), img.getOffY());
        }

        for (LightWrapper lgh : lights) {
            drawLight(lgh.getLight(), lgh.getX(), lgh.getY());
        }

        for (int i = 0; i < pixels.length; i++) {
            float red = ((lightPixels[i] >> 16) & 0xff) / 255f;
            float green = ((lightPixels[i] >> 8) & 0xff) / 255f;
            float blue = ((lightPixels[i]) & 0xff) / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int) (((pixels[i]) & 0xff) * blue));
        }

        images.clear();
        lights.clear();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            depths[i] = 0;
            lightPixels[i] = 0xff6b6b6b;
            brightness[i] = false;
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

        if (outOfBounds(offX, -width, WIDTH, offY, -height, HEIGHT))
            return;

        int startX = 0;
        int startY = 0;

        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= WIDTH)
            width -= (width + offX - WIDTH);
        if (height + offY >= HEIGHT)
            height -= (height + offY - HEIGHT);

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }

    public void addImage(Image image, int offX, int offY) {
        images.add(new ImageWrapper(image, depth, offX, offY));
    }

    private void drawImage(Image image, int offX, int offY) {
        camera.centerCamera();

        int camX = camera.getCamX();
        int camY = camera.getCamY();
        int maxViewX = camera.getMaxViewX();
        int maxViewY = camera.getMaxViewY();

        int startX = 0;
        int startY = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        if (offX < camX)
            startX -= (offX - camX);
        if (offY < camY)
            startY -= (offY - camY);
        if (offX + width >= maxViewX)
            width -= (width + offX - maxViewX);
        if (offY + height >= maxViewY)
            height -= (height + offY - maxViewY);

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                setPixel(x + offX - camX, y + offY - camY, image.getPixels()[x + y * image.getWidth()]);
                setBrightness(x + offX, y + offY, image.isOpaque());
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

    public void addLight(Light light, int offX, int offY) {
        lights.add(new LightWrapper(light, offX, offY));
    }

    private void drawLight(Light light, int offX, int offY) {
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

            if (outOfBounds(screenX, 0, WIDTH, screenY, 0, HEIGHT))
                return;

            int lightColor = light.getLightValue(x0, y0);

            if (lightColor == 0)
                return;

            if (brightness[screenX + screenY * WIDTH])
                return;

            setLightPixels(screenX, screenY, lightColor);

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

    private void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;

        if (outOfBounds(x, 0, WIDTH, y, 0, HEIGHT) || alpha == 0)
            return;

        int index = x + y * camera.getMapWidthInPixel();

        if (depths[index] > depth)
            return;

        depths[index] = depth;

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

    private void setLightPixels(int x, int y, int value) {
        if (outOfBounds(x, 0, WIDTH, y, 0, HEIGHT))
            return;

        int baseColor = lightPixels[x + y * WIDTH];
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        lightPixels[x + y * WIDTH] = (maxRed << 16 | maxGreen << 8 | maxBlue);

    }

    private void setBrightness(int x, int y, boolean value) {
        if (outOfBounds(x, 0, WIDTH, y, 0, HEIGHT))
            return;
        if (depths[x + y * WIDTH] > depth)
            return;
        brightness[x + y * WIDTH] = value;
    }

    private boolean outOfBounds(int width, int lw, int rw, int height, int lh, int rh) {
        return width < lw || width >= rw || height < lh || height >= rh;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Camera getCamera() {
        return camera;
    }
}
