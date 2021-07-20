package com.company.weapons;

import com.company.entities.human.Entity;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.physics.basics.Vector;

import java.io.Serializable;

public abstract class Weapon implements Serializable {
    private final int damage;
    private final int lengthWidth;
    private final int lengthHeight;

    public Weapon(int damage, int lengthWidth, int lengthHeight) {
        this.damage = damage;
        this.lengthWidth = lengthWidth;
        this.lengthHeight = lengthHeight;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void effect();

    public Rectangle getRectangleAttack(Entity entity) {
        switch (entity.getFacing()) {
            case NORTH:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() - lengthHeight), 0xff000000, false);
            case EAST:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - lengthWidth, entity.getPosY() + lengthHeight), 0xff000000, false);
            case WEST:
                return new Rectangle(new Vector(entity.getPosX() + GameLoop.TILE_WIDTH, entity.getPosY()), new Vector(entity.getPosX() + GameLoop.TILE_WIDTH + lengthWidth, entity.getPosY() + lengthHeight), 0xff000000, false);
            default:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY() + GameLoop.TILE_HEIGHT), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() + GameLoop.TILE_HEIGHT + lengthHeight), 0xff000000, false);
        }
    }
}
