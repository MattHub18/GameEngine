package com.company.entities.objects;

import java.io.Serializable;

public class Healer implements Healing, Serializable {
    private final int healingPower;

    public Healer(int healingPower) {
        this.healingPower = healingPower;
    }

    @Override
    public int getHealingPower() {
        return healingPower;
    }
}
