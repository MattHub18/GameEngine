package com.company.world;

import com.company.entities.EntityManager;
import com.company.entities.human.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;
import java.util.HashMap;

public abstract class World implements Graphic, Serializable {

    protected Room currentRoom;
    protected EntityManager entityManager;
    protected HashMap<Integer, Room> worldMap;
    protected GameEntity player;

    public World() {
        worldMap = new HashMap<>();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entityManager.update(gl, dt, this);
        transition();
        currentRoom.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        currentRoom.render(gl, r);
        entityManager.render(gl, r);
    }


    public void collisions(GameEntity e) {
        currentRoom.collisions(e);
    }

    public int getWidthInPixel() {
        return currentRoom.getWidthInPixel();
    }

    public int getHeightInPixel() {
        return currentRoom.getHeightInPixel();
    }

    public abstract void transition();

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public GameEntity getPlayer() {
        return player;
    }

    public void init() {
        entityManager.init();
        entityManager.addEntity(player);
        currentRoom.spawnEntities();
    }
}
