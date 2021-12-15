package com.company.weapons;

import com.company.entities.human.Entity;
import com.company.entities.objects.Content;
import com.company.graphic.gfx.Rectangle;
import com.company.physics.basics.Vector;

import java.io.Serializable;

import static com.company.directions.SystemFacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class Weapon implements Content, Serializable {
    private final int damage;
    private final int lengthWidth;
    private final int lengthHeight;
    private final byte icon;
    private final String name;

    public Weapon(int damage, int lengthWidth, int lengthHeight, byte icon, String name) {
        this.damage = damage;
        this.lengthWidth = lengthWidth;
        this.lengthHeight = lengthHeight;
        this.icon = icon;
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getBox(Entity entity) {
        byte facingDirection = entity.getFacingDirection();
        if (facingDirection == NORTH())
            return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() - lengthHeight));
        else if (facingDirection == SOUTH())
            return new Rectangle(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() + TILE_HEIGHT() + lengthHeight));
        else if (facingDirection == WEST())
            return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - lengthWidth, entity.getPosY() + lengthHeight));
        else//east
            return new Rectangle(new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH() + lengthWidth, entity.getPosY() + lengthHeight));
    }

    @Override
    public byte getIcon() {
        return icon;
    }

    @Override
    public String getName() {
        return name;
    }
}
