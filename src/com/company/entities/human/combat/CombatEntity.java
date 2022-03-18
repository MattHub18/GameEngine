package com.company.entities.human.combat;

import com.company.death_strategy.Death;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.human.healer.HealProperty;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.weapons.Weapon;
import com.company.world.Room;

import java.io.Serializable;

public class CombatEntity implements Graphic, GameEntity, CombatInterface, Damageable, HealProperty, Serializable {

    protected final Entity entity;
    protected final DamageableEntity damageableEntity;
    protected AttackEntity attackEntity;

    public CombatEntity(Entity entity, EntityGraphicComponent attackComponent, int lifePoints, Weapon weapon, Death death) {
        this.entity = entity;
        this.attackEntity = new AttackEntity(entity, attackComponent, weapon);
        this.damageableEntity = new DamageableEntity(lifePoints, death);
    }

    protected CombatEntity(Entity entity, AttackEntity attackEntity, DamageableEntity damageableEntity) {
        this.entity = entity;
        this.attackEntity = attackEntity;
        this.damageableEntity = damageableEntity;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        attackEntity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        attackEntity.render(gl, r);
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
        return new CombatEntity((Entity) entity.copy(), (AttackEntity) attackEntity.copy(), damageableEntity.copy());
    }

    @Override
    public boolean isAttacking() {
        return attackEntity.isAttacking();
    }

    @Override
    public void meleeAttack() {
        attackEntity.meleeAttack();
    }

    @Override
    public int getLifePoints() {
        return damageableEntity.getLifePoints();
    }

    @Override
    public void setLifePoints(int lp) {
        damageableEntity.setLifePoints(lp);
    }

    @Override
    public int getMaxLifePoints() {
        return damageableEntity.getLifePoints();
    }

    @Override
    public void receiveDamage(GameEntity entity, int damage) {
        damageableEntity.receiveDamage(entity, damage);
    }

    @Override
    public boolean isDead() {
        return damageableEntity.isDead();
    }

    @Override
    public void setDead(boolean dead) {
        damageableEntity.setDead(dead);
    }

    @Override
    public int getHealProperty() {
        return getLifePoints();
    }

    @Override
    public void setHealProperty(int value) {
        setLifePoints(value);
    }

    @Override
    public int getMaxHealProperty() {
        return getMaxLifePoints();
    }
}
