package com.company.entities.human.healer;

import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.objects.Healer;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

import java.io.Serializable;

public class HealerEntity implements Graphic, GameEntity, Serializable {
    private final Entity entity;
    private final EntityGraphicComponent component;

    public HealerEntity(Entity entity, EntityGraphicComponent component) {
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
        return new HealerEntity((Entity) entity.copy(), component.copy());
    }

    public void healing(HealProperty healPropertyEntity, Healer healer) {
        int v = healPropertyEntity.getHealProperty() + healer.getHealingPower();
        int maxV = healPropertyEntity.getMaxHealProperty();
        if (v > maxV)
            v = maxV;
        healPropertyEntity.setHealProperty(v);
    }
}
