package com.company.entities.objects;

import com.company.entities.human.interaction.InteractObject;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.Room;

public abstract class Chest extends InteractObject {

    protected final ContentDisplay display;
    private boolean closed;

    public Chest(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection, ContentDisplay display) {
        super(textureFilename, posX, posY, maxFrames, room, facingDirection);
        this.display = display;
        closed = false;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
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
    }
}
