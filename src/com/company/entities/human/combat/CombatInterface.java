package com.company.entities.human.combat;

import com.company.entities.human.GameEntity;

public interface CombatInterface {

    boolean isAttack();

    int getLifePoints();

    void setLifePoints(int lp);

    int getMaxLifePoints();

    void meleeAttack();

    void receiveDamage(GameEntity entity, int damage);

    boolean isDead();

    void setDead(boolean dead);
}
