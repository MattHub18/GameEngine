package com.company.entities.human.interaction;

import com.company.directions.SystemFacingDirections;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.objects.Interactive;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class InteractEntity implements Graphic, GameEntity, InteractInterface, Serializable {
    private final Entity entity;
    private final EntityGraphicComponent component;

    public InteractEntity(Entity entity, EntityGraphicComponent component) {
        this.entity = entity;
        this.component = component;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (component != null)
            component.update(dt);
        else
            entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (component != null)
            component.render(r, entity.getPosX(), entity.getPosY(), entity.getFacingDirection());
        else
            entity.render(gl, r);
    }

    @Override
    public int getPosX() {
        return entity.getPosX();
    }

    @Override
    public void setPosX(int posX) {
        entity.setPosX(posX);
    }

    @Override
    public int getPosY() {
        return entity.getPosY();
    }

    @Override
    public void setPosY(int posY) {
        entity.setPosY(posY);
    }

    @Override
    public Room getRoom() {
        return entity.getRoom();
    }

    @Override
    public void setRoom(Room room) {
        entity.setRoom(room);
    }

    @Override
    public AxisAlignedBoundingBox getBox() {
        return entity.getBox();
    }

    @Override
    public byte getFacingDirection() {
        return entity.getFacingDirection();
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
        entity.handleCollisionWith(tileBox);
    }

    @Override
    public GameEntity copy() {
        return new InteractEntity((Entity) entity.copy(), component.copy());
    }

    @Override
    public void interact() {
        AxisAlignedBoundingBox interaction = generateInteraction();

        for (GameEntity e : entity.getRoom().getEntityManager().getEntities()) {
            if (e instanceof Interactive) {
                if (CollisionDetector.isCollided(interaction, e.getBox())) {
                    Interactive object = (Interactive) e;
                    if (object.isOn())
                        object.off();
                    else
                        object.on();
                    break;
                }
            }
        }
    }

    private AxisAlignedBoundingBox generateInteraction() {
        byte facingDirection = entity.getFacingDirection();
        if (facingDirection == SystemFacingDirections.NORTH)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH, entity.getPosY() - TILE_HEIGHT));
        else if (facingDirection == SystemFacingDirections.SOUTH)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY() + TILE_HEIGHT), new Vector(entity.getPosX() + TILE_WIDTH, entity.getPosY() + TILE_HEIGHT + TILE_HEIGHT));
        else if (facingDirection == SystemFacingDirections.WEST)
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX(), entity.getPosY()), new Vector(entity.getPosX() - TILE_WIDTH, entity.getPosY() + TILE_HEIGHT));
        else//east
            return new AxisAlignedBoundingBox(new Vector(entity.getPosX() + TILE_WIDTH, entity.getPosY()), new Vector(entity.getPosX() + TILE_WIDTH + TILE_WIDTH, entity.getPosY() + TILE_HEIGHT));
    }
}
