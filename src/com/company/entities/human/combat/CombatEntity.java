package com.company.entities.human.combat;

import com.company.entities.human.deathStrategy.Death;
import com.company.weapons.Weapon;

import java.io.Serializable;

public class CombatEntity implements Serializable, CombatInterface {
    private int lifePoints;
    private int maxLifePoints;
    private Weapon weapon;
    private boolean alive;
    private Death deathStrategy;

    public CombatEntity(int lifePoints, Weapon weapon, Death deathStrategy) {
        this.lifePoints = lifePoints;
        this.weapon = weapon;
        this.deathStrategy = deathStrategy;
        this.alive = true;
        this.maxLifePoints = lifePoints;
    }

    public CombatEntity copy(CombatEntity copy) {
        this.lifePoints = copy.lifePoints;
        this.maxLifePoints = copy.maxLifePoints;
        this.weapon = copy.weapon;
        this.alive = copy.alive;
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
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void meleeAttack(CombatEntity entity) {
        if (weapon != null)
            basicAttack(entity, weapon.getDamage());
    }

    @Override
    public void basicAttack(CombatEntity entity, int damage) {
        if (entity != null) {
            if (handleAttackCollision(entity)) {
                entity.receiveDamage(damage);
            }
        }
    }

    @Override
    public boolean handleAttackCollision(CombatEntity entity) {
        //TODO IMPLEMENT ME
        return false;
    }

    @Override
    public void receiveDamage(int damage) {
        lifePoints -= damage;
        if (lifePoints <= 0)
            deathStrategy.die();
    }
}
