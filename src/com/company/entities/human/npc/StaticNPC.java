package com.company.entities.human.npc;

import com.company.entities.human.GameEntity;
import com.company.entities.human.interaction.InteractObject;
import com.company.graphic.primitives.GameLoop;
import com.company.world.Room;

public class StaticNPC extends InteractObject {

    public StaticNPC(byte textureFilename, int posX, int posY, int maxFrames, Room room, byte facingDirection) {
        super(textureFilename, posX, posY, maxFrames, room, facingDirection);
    }

    @Override
    public GameEntity copy() {
        return new StaticNPC(entity.getTextureFilename(), entity.getPosX(), entity.getPosY(), entity.getMaxFrames(), entity.getRoom(), entity.getFacingDirection());
    }

    @Override
    public void update(GameLoop gl, float dt) {
        super.update(gl, dt);
        if (on) {
            gl.nextState("Dialog-Dialog");
            off();
        }
    }
}

