package com.company.entities.objects;

import java.io.Serializable;

public class Healer implements Healing, Content, Serializable {
    private final int healingPower;
    private final byte icon;
    private final String name;

    public Healer(int healingPower, byte icon, String name) {
        this.healingPower = healingPower;
        this.icon = icon;
        this.name = name;
    }

    @Override
    public int getHealingPower() {
        return healingPower;
    }

    @Override
    public byte getIcon() {
        return icon;
    }

    @Override
    public String getName() {
        return name;
    }
}
