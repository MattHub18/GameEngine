package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Light;

import java.util.ArrayList;

public class LightRender implements RenderInterface {
    private final ArrayList<Light> lights;
    private final BasicRender basicRender;

    public LightRender(BasicRender basicRender) {
        this.basicRender = basicRender;
        this.lights = new ArrayList<>();
    }

    @Override
    public void process() {
        for (Light lgh : lights) {
            drawLight(lgh, lgh.getOffX(), lgh.getOffY());
        }
    }

    @Override
    public void clear() {
        lights.clear();
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

        int[] structure = basicRender.cameraShift(offX, offY, light.getDiameter(), light.getDiameter(), light.isMovable());

        int camX = structure[4];
        int camY = structure[5];

        while (true) {

            int screenX = x0 + offX - camX;
            int screenY = y0 + offY - camY;

            if (basicRender.outOfBounds(screenX, screenY))
                return;

            int lightColor = light.getLightValue(x0, y0);

            if (lightColor == 0)
                return;

            if (basicRender.getBrightness(screenX, screenY))
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

    private void setLightPixels(int x, int y, int value) {
        if (basicRender.outOfBounds(x, y))
            return;

        int baseColor = basicRender.getLightPixels(x, y);
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        basicRender.setLightPixel(x, y, (maxRed << 16 | maxGreen << 8 | maxBlue));
    }
}
