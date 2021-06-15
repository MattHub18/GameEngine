package com.company.graphic.gfx;

import com.company.directions.Direction;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;

public class Particle implements Graphic {

    private static final int MAXTTL = 100;
    private final int width;
    private final int height;
    private final int[] pixels;
    private final Type type;
    private boolean alive = true;
    private int x;
    private int y;
    private int ttl = MAXTTL;
    private Vector direction;
    private int color = -1;

    public Particle(Rectangle r, int color) {
        type = Type.RECTANGLE;

        this.color = color;

        this.x = r.getStartX();
        this.y = r.getStartY();
        this.width = r.getWidth();
        this.height = r.getHeight();

        Direction dir = Direction.randomDirection();
        direction = new Vector(dir.dirX, dir.dirY);

        pixels = new int[width * height];
        for (int i = 0; i < width * height; i++)
            pixels[i] = color;
    }


    public Particle(Image i, int x, int y) {
        type = Type.IMAGE;

        this.x = x;
        this.y = y;
        this.width = i.getWidth();
        this.height = i.getHeight();

        Direction dir = Direction.randomDirection();
        direction = new Vector(dir.dirX, dir.dirY);

        pixels = i.getPixels();
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

            Direction dir = Direction.randomDirection();
            direction = new Vector(dir.dirX, dir.dirY);
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addParticle(this, x, y, width, height);
    }

    public boolean isAlive() {
        return alive;
    }

    public int[] getPixels() {
        return pixels;
    }

    public Type getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public enum Type {RECTANGLE, IMAGE}
}
