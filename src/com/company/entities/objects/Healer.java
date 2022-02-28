package com.company.entities.objects;

import java.io.Serializable;

public class Healer implements Healing, Content, Serializable {
    private final int healingPower;
    private final ContentGraphicComponent component;

    public Healer(int healingPower, ContentGraphicComponent component) {
        this.healingPower = healingPower;
        this.component = component;
    }

    @Override
    public int getHealingPower() {
        return healingPower;
    }

    @Override
    public byte getIcon() {
        return component.getIcon();
    }

    @Override
    public String getName() {
        return component.getName();
    }
}
