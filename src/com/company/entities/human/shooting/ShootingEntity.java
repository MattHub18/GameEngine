package com.company.entities.human.shooting;

import com.company.entities.bullet.Bullet;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ShootingEntity implements Graphic, GameEntity, ShootingInterface, Serializable {

    protected final Entity entity;
    protected final EntityGraphicComponent component;
    private final ArrayList<Bullet> currentQueue;
    private boolean shooting;

    public ShootingEntity(Entity entity, EntityGraphicComponent component) {
        this.entity = entity;
        this.component = component;
        this.shooting = false;
        currentQueue = new ArrayList<>();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        Iterator<Bullet> iterator = currentQueue.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update(gl, dt);
            entity.getRoom().tileCollision(bullet);
            entity.getRoom().getEntityManager().entityCollision(bullet);

            if (bullet.getMaxTime() <= 0)
                remove(iterator);
        }

        if (component != null)
            component.update(dt);
        else
            entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (Bullet bullet : currentQueue)
            bullet.render(gl, r);

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
        return new ShootingEntity((Entity) entity.copy(), component.copy());
    }

    public void addBullet(Bullet bullet) {
        currentQueue.add(bullet);
    }

    private void remove(Iterator<Bullet> iterator) {
        iterator.remove();
        shooting = false;
    }

    @Override
    public void shooting() {
        shooting = true;
    }

    @Override
    public boolean isShooting() {
        return shooting;
    }
}
