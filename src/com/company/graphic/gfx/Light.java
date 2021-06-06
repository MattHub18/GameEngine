package com.company.graphic.gfx;

public class Light {
    private final int radius;
    private final int[] lightPixels;
    private final boolean fullPower;

    public Light(int radius, int color, boolean fullPower) {
        this.radius = radius;
        int diameter = radius * 2;
        lightPixels = new int[diameter * diameter];
        this.fullPower = fullPower;

        for (int y = 0; y < diameter; y++) {
            for (int x = 0; x < diameter; x++) {
                double distance = Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
                if (distance < radius) {
                    double power = 1;
                    if (!fullPower)
                        power -= (distance / radius);
                    lightPixels[x + y * diameter] = ((int) (((color >> 16) & 0xff) * power) << 16 | (int) (((color >> 8) & 0xff) * power) << 8 | (int) (((color) & 0xff) * power));
                } else
                    lightPixels[x + y * diameter] = 0;
            }
        }
    }

    public int getLightValue(int x, int y) {
        int diameter = radius * 2;
        if (x < 0 || x >= diameter || y < 0 || y >= diameter)
            return 0;
        return lightPixels[x + y * diameter];
    }

    public int getRadius() {
        return radius;
    }

    public int getDiameter() {
        return 2 * radius;
    }

    public boolean isFullPower() {
        return fullPower;
    }
}
