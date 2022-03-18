package com.company.entities.objects;

import java.io.Serializable;

public class Healer implements Content, Serializable {
    private final int healingPower;
    private final ContentGraphicComponent component;
    private final String initial;

    public Healer(int healingPower, ContentGraphicComponent component, String initial) {
        this.healingPower = healingPower;
        this.component = component;
        this.initial = initial;
    }

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

    public String getInitial() {
        return initial;
    }
}
