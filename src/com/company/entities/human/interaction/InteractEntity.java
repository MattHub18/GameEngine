package com.company.entities.human.interaction;

import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;

public class InteractEntity implements Graphic, InteractInterface, Serializable {
    private final Entity entity;

    public InteractEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void interact() {
        System.out.println("INTERACT");
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }
}
