package com.company.entities.human.combat;

import com.company.death_strategy.Death;
import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.weapons.Weapon;

import java.io.Serializable;
import java.util.Iterator;

public class ComboCombatEntity extends CombatEntity implements Serializable {
    private int combo;
    private boolean doneDamage;

    public ComboCombatEntity(Entity entity, int lifePoints, Weapon weapon, Death deathStrategy) {
        super(entity, lifePoints, weapon, deathStrategy);

        combo = 0;
        doneDamage = false;
    }

    @Override
    protected void doDamage(GameEntity enemy, Iterator<GameEntity> enemyIterator) {
        combo++;
        if (combo == 4) {
            ((CombatInterface) enemy).receiveDamage(enemy, weapon.getDamage() * 2);
            combo = 0;
        } else
            ((CombatInterface) enemy).receiveDamage(enemy, weapon.getDamage());
        if (((CombatInterface) enemy).isDead())
            enemyIterator.remove();

        doneDamage = true;
    }

    public int getCombo() {
        return combo;
    }

    public boolean isDoneDamage() {
        return doneDamage;
    }

    public void resetDoneDamage() {
        doneDamage = false;
    }
}
