package com.company.graphic.primitives.renders;

import com.company.graphic.primitives.Camera;
import com.company.graphic.primitives.CameraShift;
import com.company.graphic.primitives.Window;

import java.awt.image.DataBufferInt;

public class BasicRender implements RenderInterface {
    private final Camera camera;
    private final int[] pixels;
    private final int[] lightPixels;
    private final boolean[] brightness;

    public BasicRender(Camera camera, Window window) {
        this.camera = camera;
        this.pixels = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();
        this.lightPixels = new int[pixels.length];
        this.brightness = new boolean[pixels.length];
    }

    @Override
    public void process() {
        for (int i = 0; i < pixels.length; i++) {
            float red = ((lightPixels[i] >> 16) & 0xff) / 255f;
            float green = ((lightPixels[i] >> 8) & 0xff) / 255f;
            float blue = ((lightPixels[i]) & 0xff) / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int) (((pixels[i]) & 0xff) * blue));
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            lightPixels[i] = 0xff6b6b6b;
            brightness[i] = false;
        }
    }

    public void setPixel(int x, int y, int value) {
        int alpha = (value >> 24) & 0xff;

        if (outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT) || alpha == 0)
            return;

        int index = x + y * camera.getMapWidthInPixel();

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


    public void setBrightness(int x, int y, boolean value) {
        if (outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT))
            return;
        int index = x + y * camera.getMapWidthInPixel();
        brightness[index] = value;
    }

    public boolean outOfBounds(int width, int lw, int rw, int height, int lh, int rh) {
        return width < lw || width >= rw || height < lh || height >= rh;
    }

    public boolean getBrightness(int x, int y) {
        return brightness[x + y * camera.getMapWidthInPixel()];
    }

    public int getLightPixels(int x, int y) {
        return lightPixels[x + y * camera.getMapWidthInPixel()];
    }

    public void setLightPixel(int x, int y, int value) {
        lightPixels[x + y * camera.getMapWidthInPixel()] = value;
    }

    public CameraShift cameraShift(int offX, int offY, int w, int h, boolean movable) {
        int startX = 0;
        int startY = 0;
        int width = w;
        int height = h;

        camera.centerCamera();

        int camX = camera.getCamX();
        int camY = camera.getCamY();
        int maxViewX = camera.getMaxViewX();
        int maxViewY = camera.getMaxViewY();

        if (movable) {
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

        return new CameraShift(startX, startY, width, height, camX, camY);
    }
}
