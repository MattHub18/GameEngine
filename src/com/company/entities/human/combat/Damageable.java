package com.company.entities.human.combat;

import com.company.entities.human.GameEntity;

public interface Damageable {
    void receiveDamage(GameEntity entity, int damage);
}
