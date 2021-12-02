package com.company.hud;

import java.io.Serializable;

public abstract class Action implements Serializable {

    private final String name;

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void doAction(Object element);
}
