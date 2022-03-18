package com.company.entities.human.combat;

import com.company.death_strategy.Death;
import com.company.entities.human.entity.GameEntity;

import java.io.Serializable;

public class DamageableEntity implements Damageable, Serializable {

    protected final Death deathStrategy;
    private final int maxLifePoints;
    private int lifePoints;
    private boolean dead;

    public DamageableEntity(int lifePoints, Death deathStrategy) {
        this.lifePoints = lifePoints;
        this.maxLifePoints = lifePoints;
        this.deathStrategy = deathStrategy;
        this.dead = false;
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public void setLifePoints(int lp) {
        lifePoints = lp;
    }

    @Override
    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    @Override
    public void receiveDamage(GameEntity entity, int damage) {
        lifePoints -= damage;
        if (lifePoints <= 0)
            deathStrategy.die(entity);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public DamageableEntity copy() {
        return new DamageableEntity(lifePoints, deathStrategy);
    }
}
