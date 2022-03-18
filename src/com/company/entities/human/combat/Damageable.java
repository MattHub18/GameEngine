package com.company.entities.human.combat;

import com.company.entities.human.entity.GameEntity;

public interface Damageable {

    int getLifePoints();

    void setLifePoints(int lp);

    int getMaxLifePoints();

    void receiveDamage(GameEntity entity, int damage);

    boolean isDead();

    void setDead(boolean dead);
}
