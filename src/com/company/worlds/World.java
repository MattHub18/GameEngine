package com.company.worlds;

import com.company.audio.Sound;
import com.company.entities.entity.GameEntity;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.physics.basics.Point;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

import java.util.HashMap;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class World implements Graphic, RenderObject {

    private final byte themeFilename;
    protected Ambient currentAmbient;
    protected HashMap<Integer, Ambient> worldMap;
    protected Point start;
    private GameEntity player;
    private Sound theme;

    public World(byte themeFilename) {
        worldMap = new HashMap<>();
        theme = null;
        this.themeFilename = themeFilename;
        startSound();
    }

    @Override
    public void update(Engine engine, float dt) {
        transition(engine);
        currentAmbient.update(engine, dt);
    }

    @Override
    public void render(Render r) {
        currentAmbient.render(r);
    }

    @Override
    public int getWidthInPixel() {
        return currentAmbient.getWidthInPixel();
    }

    @Override
    public int getHeightInPixel() {
        return currentAmbient.getHeightInPixel();
    }

    private void transition(Engine gl) {
        currentAmbient.removePlayer(player);

        if (player.getPosX() > currentAmbient.getWidth() * TILE_WIDTH) {
            player.setPosX(0);
            currentAmbient = worldMap.get(currentAmbient.RIGHT);
        } else if (player.getPosY() < 0) {
            player.setPosY(currentAmbient.getHeight() * TILE_HEIGHT);
            currentAmbient = worldMap.get(currentAmbient.UP);
        } else if (player.getPosY() > currentAmbient.getHeight() * TILE_HEIGHT) {
            player.setPosY(0);
            currentAmbient = worldMap.get(currentAmbient.DOWN);
        } else if (player.getPosX() < 0) {
            player.setPosX(currentAmbient.getWidth() * TILE_WIDTH);
            currentAmbient = worldMap.get(currentAmbient.LEFT);
        }

        player.setAmbient(currentAmbient);
        currentAmbient.addPlayer(player);
        currentAmbient.spawnEntities();
        gl.updateCamera(this);
    }

    private void init(GameEntity player) {
        this.player = player;
        currentAmbient.addPlayer(player);
        currentAmbient.spawnEntities();
    }


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

    public void continueGame(GameEntity player) {
        Ambient r = player.getAmbient();
        currentAmbient = r;
        worldMap.replace(r.getRoomId(), currentAmbient);
        init(player);
    }

    public void newGame(GameEntity player) {
        player.setAmbient(currentAmbient);
        player.setPosX(start.getX());
        player.setPosY(start.getY());
        init(player);
    }
}
