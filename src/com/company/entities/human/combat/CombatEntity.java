package com.company.entities.human.combat;

import com.company.entities.human.Entity;
import com.company.entities.human.deathStrategy.Death;
import com.company.graphic.gfx.Rectangle;
import com.company.physics.collisions.CollisionDetector;
import com.company.weapons.Weapon;

import java.io.Serializable;

public class CombatEntity implements Serializable, CombatInterface {
    private int lifePoints;
    private int maxLifePoints;
    private Weapon weapon;
    private Death deathStrategy;

    public CombatEntity(int lifePoints, Weapon weapon, Death deathStrategy) {
        this.lifePoints = lifePoints;
        this.weapon = weapon;
        this.deathStrategy = deathStrategy;
        this.maxLifePoints = lifePoints;
    }

    public CombatEntity copy(CombatEntity copy) {
        this.lifePoints = copy.lifePoints;
        this.maxLifePoints = copy.maxLifePoints;
        this.weapon = copy.weapon;
        this.deathStrategy = copy.deathStrategy;
        return this;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public void setLifePoints(int lp) {
        this.lifePoints = lp;
    }

    @Override
    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    @Override
    public void meleeAttack(Entity entity) {
        if (weapon != null)
            basicAttack(entity, weapon.getDamage());
    }

    public void basicAttack(Entity entity, int damage) {
        if (entity != null) {
            if (handleAttackCollision(entity)) {
                entity.receiveDamage(damage);
            }
        }
    }

    private boolean handleAttackCollision(Entity entity) {
        Rectangle attack = weapon.getRectangleAttack(entity);
        Rectangle enemy = entity.getBox();

        return CollisionDetector.isCollided(attack, enemy);
    }

    @Override
    public void receiveDamage(int damage) {
        lifePoints -= damage;
        if (lifePoints <= 0)
            deathStrategy.die();
    }
}
