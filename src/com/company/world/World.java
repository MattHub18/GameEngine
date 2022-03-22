package com.company.world;

import com.company.audio.Sound;
import com.company.audio.Theme;
import com.company.entities.human.entity.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.physics.basics.Point;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

import java.io.Serializable;
import java.util.HashMap;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class World implements Graphic, RenderObject, Theme, Serializable {

    protected Room currentRoom;
    protected HashMap<Integer, Room> worldMap;
    private GameEntity player;
    private Sound theme;
    private final byte themeFilename;
    protected Point start;

    public World(byte themeFilename) {
        worldMap = new HashMap<>();
        theme = null;
        this.themeFilename = themeFilename;
        startSound();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        transition(gl);
        currentRoom.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        currentRoom.render(gl, r);
    }

    @Override
    public int getWidthInPixel() {
        return currentRoom.getWidthInPixel();
    }

    @Override
    public int getHeightInPixel() {
        return currentRoom.getHeightInPixel();
    }

    private void transition(GameLoop gl) {
        currentRoom.removePlayer(player);

        if (player.getPosX() > currentRoom.getWidth() * TILE_WIDTH) {
            player.setPosX(0);
            currentRoom = worldMap.get(currentRoom.RIGHT);
        } else if (player.getPosY() < 0) {
            player.setPosY(currentRoom.getHeight() * TILE_HEIGHT);
            currentRoom = worldMap.get(currentRoom.UP);
        } else if (player.getPosY() > currentRoom.getHeight() * TILE_HEIGHT) {
            player.setPosY(0);
            currentRoom = worldMap.get(currentRoom.DOWN);
        } else if (player.getPosX() < 0) {
            player.setPosX(currentRoom.getWidth() * TILE_WIDTH);
            currentRoom = worldMap.get(currentRoom.LEFT);
        }

        player.setRoom(currentRoom);
        currentRoom.addPlayer(player);
        currentRoom.spawnEntities();
        gl.updateCamera(this);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void init(GameEntity player) {
        this.player = player;
        currentRoom.addPlayer(player);
        currentRoom.spawnEntities();
    }

    @Override
    public void stopSound() {
        if (theme != null)
            theme.close();
    }

    public void startSound() {
        if (themeFilename != SystemResources.NO_SOUND) {
            theme = new Sound(Archive.SOUND.get(themeFilename));
            theme.loop();
        }
    }

    protected void initialPosition(GameEntity player) {
        player.setPosX(start.getX());
        player.setPosY(start.getY());
        player.setRoom(currentRoom);
    }

    public void continueGame(GameEntity player) {
        Room r = player.getRoom();
        currentRoom = r;
        worldMap.replace(r.getRoomId(), currentRoom);
        init(player);
    }
}
