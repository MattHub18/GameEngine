package com.company.graphic.gfx;

public class ParticleWrapper {
    private final Particle particle;
    private final int offX;
    private final int offY;
    private final int width;
    private final int height;

    public ParticleWrapper(Particle particle, int offX, int offY, int width, int height) {
        this.particle = particle;
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
    }

    public Particle getParticle() {
        return particle;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
