package com.company.entities;

import com.company.entities.human.GameEntity;
import com.company.entities.human.movable.MovableInterface;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.world.World;

import java.io.Serializable;
import java.util.ArrayList;

public class EntityManager implements Graphic, Serializable {

    private transient ArrayList<GameEntity> entities;
    private World world;

    public EntityManager() {
        world = null;
    }

    public void update(GameLoop gl, float dt, World world) {
        if (this.world == null)
            this.world = world;
        update(gl, dt);
    }

    public void addEntity(GameEntity e) {
        entities.add(e);
    }


    @Override
    public void update(GameLoop gl, float dt) {
        for (GameEntity e : entities) {
            if (isInCurrentRoom(e))
                entityUpdate(e, gl, dt);
        }
    }

    private void entityUpdate(GameEntity e, GameLoop gl, float dt) {
        if (e instanceof MovableInterface)
            ((MovableInterface) e).clearMove();
        e.update(gl, dt);
        if (e instanceof MovableInterface)
            world.collisions(e);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (GameEntity e : entities)
            e.render(gl, r);
    }

    public ArrayList<GameEntity> getEntities() {
        return entities;
    }

    public boolean isInCurrentRoom(GameEntity e) {
        if (world == null)
            return true;
        return e.getRoom().equals(world.getCurrentRoom());
    }

    public void init() {
        entities = new ArrayList<>();
    }
}
