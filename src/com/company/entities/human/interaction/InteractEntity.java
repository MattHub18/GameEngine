package com.company.entities.human.interaction;

import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.entities.objects.Chest;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;

import java.io.Serializable;

import static com.company.directions.FacingDirections.*;
import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class InteractEntity implements Graphic, InteractInterface, Serializable {
    private final Entity entity;

    public InteractEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void interact() {
        Rectangle interaction;
        switch (entity.getFacingDirection()) {
            case NORTH:
                interaction = new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY() - TILE_HEIGHT()));
                break;
            case WEST:
                interaction = new Rectangle(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT()));
                break;
            case EAST:
                interaction = new Rectangle(new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH() + TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT()));
                break;
            default:
                interaction = new Rectangle(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT()), new Vector(entity.getPosX() + TILE_WIDTH(), entity.getPosY() + TILE_HEIGHT() + TILE_HEIGHT()));
        }

        for (GameEntity e : entity.getRoom().getEntityManager().getEntities()) {
            if (e instanceof Chest) {
                if (CollisionDetector.isCollided(interaction, e.getBox())) {
                    Chest chest = (Chest) e;
                    if (chest.isOpen())
                        chest.close();
                    else
                        chest.open();
                    break;
                }
            }
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
