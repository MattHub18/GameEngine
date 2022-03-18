package com.company.entities.human.combat;

import com.company.entities.EntityManager;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.collisions.CollisionDetector;
import com.company.weapons.Weapon;
import com.company.world.Room;

import java.io.Serializable;
import java.util.Iterator;

public class AttackEntity implements Graphic, GameEntity, CombatInterface, Serializable {

    protected final Entity entity;
    protected final EntityGraphicComponent component;
    protected final Weapon weapon;
    private boolean attack;

    public AttackEntity(Entity entity, EntityGraphicComponent component, Weapon weapon) {
        this.entity = entity;
        this.component = component;
        this.weapon = weapon;
        this.attack = false;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (component != null) {
            component.update(dt);
            if (component.getAnimationFrame() == 0f)
                attack = false;
        } else
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
        return new AttackEntity((Entity) entity.copy(), component.copy(), weapon);
    }

    @Override
    public boolean isAttacking() {
        return attack;
    }

    @Override
    public void meleeAttack() {
        attack = true;

        EntityManager entityManager = entity.getRoom().getEntityManager();
        Iterator<GameEntity> enemyIterator = entityManager.getEntities().iterator();
        while (enemyIterator.hasNext()) {
            GameEntity enemy = enemyIterator.next();
            if (enemy instanceof Damageable) {
                if (handleAttackCollision(enemy)) {
                    doDamage(enemy, enemyIterator);
                }
            }
        }
    }

    private boolean handleAttackCollision(GameEntity enemy) {
        AxisAlignedBoundingBox attack = weapon.getBox(entity);
        AxisAlignedBoundingBox enemyBox = enemy.getBox();
        return CollisionDetector.isCollided(attack, enemyBox);
    }


    protected void doDamage(GameEntity enemy, Iterator<GameEntity> enemyIterator) {
        ((Damageable) enemy).receiveDamage(enemy, weapon.getDamage());
        if (((Damageable) enemy).isDead())
            enemyIterator.remove();
    }
}
