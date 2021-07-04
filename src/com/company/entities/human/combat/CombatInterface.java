package com.company.entities.human.combat;

public interface CombatInterface {
    int getLifePoints();

    void setLifePoints(int lp);

    int getMaxLifePoints();

    boolean isAlive();

    void setAlive(boolean alive);

    void meleeAttack(CombatEntity entity);

    void basicAttack(CombatEntity entity, int damage);

    boolean handleAttackCollision(CombatEntity entity);

    void receiveDamage(int damage);
}
