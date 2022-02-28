package com.company.entities;

import com.company.entities.human.GameEntity;
import com.company.entities.human.movable.MovableInterface;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;
import java.util.ArrayList;

public class EntityManager implements Graphic, Serializable {

    private transient ArrayList<GameEntity> entities;
    private transient ArrayList<GameEntity> toBeEliminated;

    public EntityManager() {
        init();
    }

    public void addEntity(GameEntity e) {
        try {
            entities.add(e);
        } catch (NullPointerException ex) {
            init();
            addEntity(e);
        }
    }


    @Override
    public void update(GameLoop gl, float dt) {
        for (GameEntity e : entities) {
            if (e instanceof MovableInterface)
                ((MovableInterface) e).clearMove();
            e.update(gl, dt);
            entityCollision(e);
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (GameEntity e : entities)
            e.render(gl, r);
        erase();
    }

    private void erase() {
        for (GameEntity e : toBeEliminated)
            entities.remove(e);
        toBeEliminated.clear();
    }

    public ArrayList<GameEntity> getEntities() {
        return entities;
    }

    public void entityCollision(GameEntity e) {
        for (GameEntity o : entities)
            if (!e.equals(o))
                e.handleCollisionWith(o.getBox());
    }

    public void removeEntity(GameEntity player) {
        entities.remove(player);
    }

    public void setToBeEliminated(GameEntity e) {
        toBeEliminated.add(e);
    }

    private void init() {
        entities = new ArrayList<>();
        toBeEliminated = new ArrayList<>();
    }
}
