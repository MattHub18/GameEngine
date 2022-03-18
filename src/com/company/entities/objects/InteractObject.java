package com.company.entities.objects;

import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.world.Room;

public abstract class InteractObject extends Entity implements Interactive {
    protected boolean on;

    public InteractObject(int posX, int posY, byte facingDirection, Room room, EntityGraphicComponent component) {
        super(posX, posY, facingDirection, room, component);
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
    }

    @Override
    public void on() {
        on = true;
    }

    @Override
    public void off() {
        on = false;
    }

    @Override
    public boolean isOn() {
        return on;
    }
}
