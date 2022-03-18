package com.company.entities.human.combat;

import com.company.death_strategy.Death;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.weapons.Weapon;

import java.io.Serializable;

public class ComboCombatEntity extends CombatEntity implements ComboInterface, Serializable {


    public ComboCombatEntity(Entity entity, EntityGraphicComponent component, int lifePoints, Weapon weapon, Death deathStrategy, int maxCombo, int mul) {
        super(entity, component, lifePoints, weapon, deathStrategy);

        attackEntity = new ComboAttackEntity(entity, component, weapon, maxCombo, mul);

    }

    private ComboCombatEntity(Entity entity, AttackEntity attackEntity, DamageableEntity damageableEntity) {
        super(entity, attackEntity, damageableEntity);
    }

    @Override
    public GameEntity copy() {
        return new ComboCombatEntity((Entity) entity.copy(), (AttackEntity) attackEntity.copy(), damageableEntity.copy());
    }

    @Override
    public int getCombo() {
        return ((ComboInterface) attackEntity).getCombo();
    }

    @Override
    public boolean isDoneDamage() {
        return ((ComboInterface) attackEntity).isDoneDamage();
    }

    @Override
    public void resetDoneDamage() {
        ((ComboInterface) attackEntity).resetDoneDamage();
    }
}
