package com.company.weapons;

import com.company.entities.human.Entity;
import com.company.entities.objects.Content;
import com.company.entities.objects.ContentGraphicComponent;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;

import java.io.Serializable;

import static com.company.directions.SystemFacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class Weapon implements Content, Serializable {
    private final int damage;
    private final int lengthWidth;
    private final int lengthHeight;
    private final ContentGraphicComponent component;

    public Weapon(int damage, int lengthWidth, int lengthHeight, ContentGraphicComponent component) {
        this.damage = damage;
        this.lengthWidth = lengthWidth;
        this.lengthHeight = lengthHeight;
        this.component = component;
    }

    public int getDamage() {
        return damage;
    }

    public AxisAlignedBoundingBox getBox(Entity entity) {
        byte facingDirection = entity.getFacingDirection();
        if (facingDirection == NORTH)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() - lengthHeight));
        else if (facingDirection == SOUTH)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT), new Vector(entity.getPosX() + lengthWidth, entity.getPosY() + TILE_HEIGHT + lengthHeight));
        else if (facingDirection == WEST)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - lengthWidth, entity.getPosY() + lengthHeight));
        else//east
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX() + TILE_WIDTH, entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH + lengthWidth, entity.getPosY() + lengthHeight));
    }

    @Override
    public byte getIcon() {
        return component.getIcon();
    }

    @Override
    public String getName() {
        return component.getName();
    }
}
