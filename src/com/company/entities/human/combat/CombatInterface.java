package com.company.entities.human.combat;

public interface CombatInterface {

    boolean isAttack();

    int getLifePoints();

    void setLifePoints(int lp);

    int getMaxLifePoints();

    void meleeAttack();

    boolean isDead();

    void setDead(boolean dead);
}
