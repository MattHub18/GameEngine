package com.company.entities.objects;

import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.event.Event;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.Room;

public abstract class Chest extends InteractObject {

    protected final ContentDisplay display;
    private boolean closed;
    private final String eventId;

    public Chest(int posX, int posY, byte facingDirection, Room room, EntityGraphicComponent component, ContentDisplay display, String eventId) {
        super(posX, posY, facingDirection, room, component);
        this.display = display;
        this.eventId = eventId;
        closed = false;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        super.render(gl, r);
        if (on) {
            display.show(r, this);
        }
    }

    @Override
    public void on() {
        if (!closed)
            super.on();
    }

    @Override
    public void off() {
        super.off();
        closed = true;
        getRoom().getEntityManager().setToBeEliminated(this);
        Event.updateEvent(eventId);
    }
}
