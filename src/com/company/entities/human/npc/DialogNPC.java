package com.company.entities.human.npc;

import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.objects.InteractObject;
import com.company.graphic.primitives.GameLoop;
import com.company.world.Room;

public class DialogNPC extends InteractObject {

    public DialogNPC(int posX, int posY, byte facingDirection, Room room, EntityGraphicComponent component) {
        super(posX, posY, facingDirection, room, component);
    }


    @Override
    public void update(GameLoop gl, float dt) {
        super.update(gl, dt);
        if (on) {
            gl.nextState("Dialog");
            off();
        }
    }
}
