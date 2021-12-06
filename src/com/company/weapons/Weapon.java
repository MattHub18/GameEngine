package com.company.weapons;

import com.company.entities.human.Entity;
import com.company.entities.objects.Content;
import com.company.graphic.gfx.Rectangle;
import com.company.physics.basics.Vector;

import java.io.Serializable;

import static com.company.directions.FacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class Weapon implements Content, Serializable {
    private final int damage;
    private final int lengthWidth;
    private final int lengthHeight;
    private final byte icon;

    public Weapon(int damage, int lengthWidth, int lengthHeight, byte icon) {
        this.damage = damage;
        this.lengthWidth = lengthWidth;
        this.lengthHeight = lengthHeight;
        this.icon = icon;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getBox(Entity entity) {
        switch (entity.getFacingDirection()) {
            case NORTH:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() - lengthHeight));
            case WEST:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - lengthWidth, entity.getPosY() + lengthHeight));
            case EAST:
                return new Rectangle(new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH() + lengthWidth, entity.getPosY() + lengthHeight));
            default:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() + TILE_HEIGHT() + lengthHeight));
        }
    }

    @Override
    public byte getIcon() {
        return icon;
    }
}
