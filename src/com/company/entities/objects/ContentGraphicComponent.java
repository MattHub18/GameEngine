package com.company.entities.objects;

import java.io.Serializable;

public class ContentGraphicComponent implements Content, Serializable {
    private final byte icon;
    private final String name;

    public ContentGraphicComponent(byte icon, String name) {
        this.icon = icon;
        this.name = name;
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
