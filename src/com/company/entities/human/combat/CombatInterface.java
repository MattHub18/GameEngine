package com.company.entities.human.combat;

import com.company.entities.human.Entity;

public interface CombatInterface {
    int getLifePoints();

    void setLifePoints(int lp);

    int getMaxLifePoints();

    void meleeAttack(Entity entity);

    void receiveDamage(int damage);
}
