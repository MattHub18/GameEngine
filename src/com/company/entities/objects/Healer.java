package com.company.entities.objects;

import java.io.Serializable;

public class Healer implements Healing, Content, Serializable {
    private final int healingPower;
    private final byte icon;

    public Healer(int healingPower, byte icon) {
        this.healingPower = healingPower;
        this.icon = icon;
    }

    @Override
    public int getHealingPower() {
        return healingPower;
    }

    @Override
    public byte getIcon() {
        return icon;
    }
}
