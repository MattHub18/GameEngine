package com.company.graphic.particles;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Image;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;

import java.util.Random;

public class Particle implements Graphic {

    private int[] pixels;

    private final int width;
    private final int height;
    private Type type;
    private int ttl;
    private boolean alive = true;
    private int x;
    private int y;
    private Vector direction;
    private int color = -1;

    private final boolean movable;
    private final boolean opaque;

    private Particle(int x, int y, int w, int h, int ttl, boolean movable, boolean opaque) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.ttl = ttl;
        direction = randomDirection();

        this.movable = movable;
        this.opaque = opaque;
    }

    public Particle(Rectangle r, int color, int ttl, boolean movable, boolean opaque) {
        this((int) r.getStartX(), (int) r.getStartY(), (int) r.getWidth(), (int) r.getHeight(), ttl, movable, opaque);
        this.type = Type.RECTANGLE;
        this.color = color;
        pixels = new int[width * height];
        for (int i = 0; i < width * height; i++)
            pixels[i] = color;
    }

    public Particle(Image i, int x, int y, int ttl, boolean movable, boolean opaque) {
        this(x, y, i.getWidth(), i.getHeight(), ttl, movable, opaque);
        type = Type.IMAGE;
        pixels = i.getPixels();
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (type == Type.RECTANGLE)
            r.addRectangle(new Rectangle(new Vector(x, y), new Vector(width, height), color, true, movable));
        else if (type == Type.IMAGE)
            r.addImage(new Image(pixels, width, height, movable, opaque), x, y);
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (alive) {
            this.x += direction.getX();
            this.y += direction.getY();
            for (int i = 0; i < width * height; i++) {
                int a = pixels[i] >>> 24;
                a -= 2;
                if (a <= 0) {
                    alive = false;
                    break;
                } else
                    pixels[i] = (pixels[i] & 0x00ffffff) + (a << 24);
            }
            ttl--;
            if (ttl <= 0)
                alive = false;

            direction = randomDirection();
        }
    }

    public enum Type {RECTANGLE, IMAGE}

    public boolean isAlive() {
        return alive;
    }

    private Vector randomDirection() {
        float r = new Random().nextFloat();
        if (r < 0.25)
            return new Vector(0, 1);
        else if (r >= 0.25 && r < 0.5)
            return new Vector(0, -1);
        else if (r >= 0.5 && r < 0.75)
            return new Vector(1, 0);
        else
            return new Vector(-1, 0);
    }
}
