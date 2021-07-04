package com.company.weapons;

import java.io.Serializable;

public abstract class Weapon implements Serializable {
    private final int damage;

    public Weapon(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void effect();
}
