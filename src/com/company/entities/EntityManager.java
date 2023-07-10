package com.company.entities;

import com.company.entities.entity.GameEntity;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;

import java.util.ArrayList;

public class EntityManager implements Graphic {

    private final ArrayList<GameEntity> entities;

    private final ArrayList<GameEntity> toBeEliminated;

    public EntityManager() {
        entities = new ArrayList<>();
        toBeEliminated = new ArrayList<>();
    }

    public void addEntity(GameEntity e) {
        entities.add(e);
    }


    @Override
    public void update(Engine engine, float dt) {
        for (GameEntity e : entities) {
            ((Graphic) e).update(engine, dt);
            entityCollision(e);
        }
    }

    @Override
    public void render(Render r) {
        for (GameEntity e : entities)
            ((Graphic) e).render(r);
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
}
