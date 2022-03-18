package com.company.entities.human.combat;

import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.weapons.Weapon;

import java.io.Serializable;
import java.util.Iterator;

public class ComboAttackEntity extends AttackEntity implements ComboInterface, Serializable {

    private final int maxCombo;
    private final int mul;
    private int combo;
    private boolean doneDamage;

    public ComboAttackEntity(Entity entity, EntityGraphicComponent component, Weapon weapon, int maxCombo, int mul) {
        super(entity, component, weapon);
        this.maxCombo = maxCombo;
        this.mul = mul;
        this.combo = 0;
        this.doneDamage = false;
    }

    @Override
    public GameEntity copy() {
        return new ComboAttackEntity((Entity) entity.copy(), component.copy(), weapon, maxCombo, mul);
    }

    @Override
    protected void doDamage(GameEntity enemy, Iterator<GameEntity> enemyIterator) {
        combo++;
        if (combo == maxCombo) {
            ((Damageable) enemy).receiveDamage(enemy, weapon.getDamage() * mul);
            combo = 0;
        } else
            ((Damageable) enemy).receiveDamage(enemy, weapon.getDamage());
        if (((Damageable) enemy).isDead())
            enemyIterator.remove();

        doneDamage = true;
    }

    @Override
    public int getCombo() {
        return combo;
    }

    @Override
    public boolean isDoneDamage() {
        return doneDamage;
    }

    @Override
    public void resetDoneDamage() {
        doneDamage = false;
    }
}
