package com.company.graphic.primitives;

import com.company.graphic.gfx.*;
import com.company.physics.basics.Vector;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Render {
    private final int[] pixels;

    private final ArrayList<Image> images;
    private final ArrayList<Light> lights;
    private final ArrayList<Rectangle> rectangles;
    private final ArrayList<Font> fonts;
    private final ArrayList<Circle> circles;
    private final int[] lightPixels;
    private final boolean[] brightness;

    private final Camera camera;
    private Font fps;

    public Render(Camera camera, Window window) {
        this.pixels = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();

        this.camera = camera;

        this.images = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.rectangles = new ArrayList<>();
        this.fonts = new ArrayList<>();
        this.circles = new ArrayList<>();

        this.lightPixels = new int[pixels.length];
        this.brightness = new boolean[pixels.length];
    }

    public void process() {

        for (Image img : images) {
            drawImage(img, img.getOffX(), img.getOffY());
        }

        for (Rectangle rec : rectangles) {
            if (rec.isFull())
                drawFullRectangle(rec);
            else
                drawBorderRectangle(rec, rec.getColor());
        }

        for (Circle cir : circles) {
            drawCircumference(cir, cir.getColor(), cir.isFull());
        }

        if (fps != null)
            drawFont(fps, fps.getText(), fps.getOffX(), fps.getOffY(), fps.getColor());

        for (Font font : fonts) {
            drawFont(font, font.getText(), font.getOffX(), font.getOffY(), font.getColor());
        }

        for (Light lgh : lights) {
            drawLight(lgh, lgh.getOffX(), lgh.getOffY());
        }

        for (int i = 0; i < pixels.length; i++) {
            float red = ((lightPixels[i] >> 16) & 0xff) / 255f;
            float green = ((lightPixels[i] >> 8) & 0xff) / 255f;
            float blue = ((lightPixels[i]) & 0xff) / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int) (((pixels[i]) & 0xff) * blue));
        }

        images.clear();
        rectangles.clear();
        fonts.clear();
        lights.clear();
        circles.clear();
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            lightPixels[i] = 0xff6b6b6b;
            brightness[i] = false;
        }
    }

    public void addRectangle(Rectangle rect) {
        rectangles.add(rect);
    }

    public void addThickRectangle(int smallestOffX, int smallestOffY, int biggestWidth, int biggestHeight, int color, int thickness) {
        for (int i = 0; i < thickness; i++) {
            addRectangle(new Rectangle(new Vector(smallestOffX + i, smallestOffY + i), new Vector(smallestOffX + biggestWidth - i, smallestOffY + biggestHeight - i), color, false));
        }
    }

    private void drawBorderRectangle(Rectangle rect, int color) {

        int offX = (int) rect.getStartX();
        int offY = (int) rect.getStartY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        if (outOfBounds(offX, -width, Window.WIDTH, offY, -height, Window.HEIGHT))
            return;

        int startX = 0;
        int startY = 0;

        if (offX < 0)
            startX -= offX;
        if (offY < 0)
            startY -= offY;
        if (width + offX >= Window.WIDTH)
            width -= (width + offX - Window.WIDTH);
        if (height + offY >= Window.HEIGHT)
            height -= (height + offY - Window.HEIGHT);

        for (int y = startY; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = startX; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    private void drawFullRectangle(Rectangle rect) {

        int offX = (int) rect.getStartX();
        int offY = (int) rect.getStartY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();

        if (width < 0 || height < 0)
            return;

        drawFullRectangle(new Rectangle(new Vector(offX + 1, offY + 1), new Vector(offX + width - 1, offY + height - 1), rect.getColor(), true));

        drawBorderRectangle(rect, rect.getColor());
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
    }

    private void drawCircumference(Circle circle, int color, boolean isFull) {
        int xc = (int) circle.getCenter().getX();
        int yc = (int) circle.getCenter().getY();
        int r = circle.getRadius();

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        if (!isFull)
            drawCircumferencePixel(xc, yc, x, y, color);
        else
            drawCircleLine(xc, yc, x, y, color);
        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;

            if (!isFull)
                drawCircumferencePixel(xc, yc, x, y, color);
            else
                drawCircleLine(xc, yc, x, y, color);
        }
    }

    private void drawCircumferencePixel(int xc, int yc, int x, int y, int color) {
        setPixel(xc + x, yc + y, color);
        setPixel(xc - x, yc + y, color);
        setPixel(xc + x, yc - y, color);
        setPixel(xc - x, yc - y, color);
        setPixel(xc + y, yc + x, color);
        setPixel(xc - y, yc + x, color);
        setPixel(xc + y, yc - x, color);
        setPixel(xc - y, yc - x, color);
    }

    private void drawCircleLine(int xc, int yc, int x, int y, int color) {
        drawLine(xc - x, yc + y, xc + x, yc + y, color);
        drawLine(xc - x, yc - y, xc + x, yc - y, color);
        drawLine(xc - y, yc + x, xc + y, yc + x, color);
        drawLine(xc - y, yc - x, xc + y, yc - x, color);
    }

    private void drawLine(int x0, int y0, int xf, int yf, int color) {

        if (y0 > yf) {
            y0 = y0 + yf;
            yf = y0 - yf;
            y0 = y0 - yf;
        }

        if (x0 > xf) {
            x0 = x0 + xf;
            xf = x0 - xf;
            x0 = x0 - xf;
        }

        for (int y = y0; y <= yf; y++) {
            for (int x = x0; x <= xf; x++) {
                setPixel(x, y, color);
            }
        }
    }

    public void addImage(Image image) {
        images.add(image);
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

        if (image.isMovable()) {
            camX = 0;
            camY = 0;
            maxViewX = camera.getMapWidthInPixel();
            maxViewY = camera.getMapHeightInPixel();
        }

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

    public void addFont(Font font) {
        fonts.add(font);
    }

    private void drawFont(Font font, String text, int offX, int offY, int color) {
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

    public void addLight(Light light) {
        lights.add(light);
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

            if (outOfBounds(screenX, 0, Window.WIDTH, screenY, 0, Window.HEIGHT))
                return;

            int lightColor = light.getLightValue(x0, y0);

            if (lightColor == 0)
                return;

            if (brightness[screenX + screenY * camera.getMapWidthInPixel()])
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

        if (outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT) || alpha == 0)
            return;

        int index = x + y * camera.getMapWidthInPixel();

        if (alpha == 255)
            try {
                pixels[index] = value;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        else {
            int pixelColor = pixels[index];
            int red = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int green = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int blue = ((pixelColor) & 0xff) - (int) ((((pixelColor) & 0xff) - ((value) & 0xff)) * (alpha / 255f));
            pixels[index] = (red << 16 | green << 8 | blue);
        }
    }

    private void setLightPixels(int x, int y, int value) {
        if (outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT))
            return;

        int baseColor = lightPixels[x + y * camera.getMapWidthInPixel()];
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        lightPixels[x + y * camera.getMapWidthInPixel()] = (maxRed << 16 | maxGreen << 8 | maxBlue);

    }

    private void setBrightness(int x, int y, boolean value) {
        if (outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT))
            return;
        int index = x + y * camera.getMapWidthInPixel();
        brightness[index] = value;
    }

    private boolean outOfBounds(int width, int lw, int rw, int height, int lh, int rh) {
        return width < lw || width >= rw || height < lh || height >= rh;
    }

    public void setFpsFont(Font font) {
        fps = font;
    }
}
