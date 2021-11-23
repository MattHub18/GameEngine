package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Light;
import com.company.graphic.primitives.Window;

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

        while (true) {

            int screenX = x0 - light.getRadius() + offX;
            int screenY = y0 - light.getRadius() + offY;

            if (basicRender.outOfBounds(screenX, 0, Window.WIDTH, screenY, 0, Window.HEIGHT))
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
        if (basicRender.outOfBounds(x, 0, Window.WIDTH, y, 0, Window.HEIGHT))
            return;

        int baseColor = basicRender.getLightPixels(x, y);
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        basicRender.setLightPixel(x, y, (maxRed << 16 | maxGreen << 8 | maxBlue));
    }
}