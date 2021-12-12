package com.company.entities.human.interaction;

import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;

import java.io.Serializable;

import static com.company.directions.FacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class InteractEntity implements Graphic, Serializable {
    private final Entity entity;

    public InteractEntity(Entity entity) {
        this.entity = entity;
    }

    public Rectangle generateInteraction() {
        switch (entity.getFacingDirection()) {
            case NORTH:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY() - TILE_HEIGHT()));
            case WEST:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT()));
            case EAST:
                return new Rectangle(new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH() + TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT()));
            default:
                return new Rectangle(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT()), new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT() + TILE_HEIGHT()));
        }
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }
}
