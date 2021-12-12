package com.company.world;

import com.company.entities.EntityManager;
import com.company.entities.human.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;

import java.io.Serializable;
import java.util.HashMap;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class World implements Graphic, RenderObject, Serializable {

    protected Room currentRoom;
    protected EntityManager entityManager;
    protected HashMap<Integer, Room> worldMap;
    private GameEntity player;

    public World() {
        worldMap = new HashMap<>();
        entityManager = new EntityManager();
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

    private void transition() {
        if (player.getPosX() > currentRoom.getWidth() * TILE_WIDTH()) {
            player.setPosX(0);
            currentRoom = worldMap.get(currentRoom.RIGHT);
        } else if (player.getPosY() < 0) {
            player.setPosY(currentRoom.getHeight() * TILE_HEIGHT());
            currentRoom = worldMap.get(currentRoom.UP);
        } else if (player.getPosY() > currentRoom.getHeight() * TILE_HEIGHT()) {
            player.setPosY(0);
            currentRoom = worldMap.get(currentRoom.DOWN);
        } else if (player.getPosX() < 0) {
            player.setPosX(currentRoom.getWidth() * TILE_WIDTH());
            currentRoom = worldMap.get(currentRoom.LEFT);
        }

        player.setRoom(currentRoom);
        currentRoom.spawnEntities();
    }


    public void collisions(GameEntity e) {
        currentRoom.collisions(e);
    }

    @Override
    public int getWidthInPixel() {
        return currentRoom.getWidthInPixel();
    }

    @Override
    public int getHeightInPixel() {
        return currentRoom.getHeightInPixel();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void init(GameEntity player) {
        this.player = player;
        Room r = this.player.getRoom();
        if (r == null)
            this.player.setRoom(currentRoom);
        else {
            currentRoom = r;
            worldMap.replace(r.getRoomId(), currentRoom);
            currentRoom.setEntityManager(entityManager);
        }
        entityManager.init();
        entityManager.addEntity(player);
        currentRoom.spawnEntities();
    }
}
