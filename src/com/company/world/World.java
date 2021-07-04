package com.company.world;

import com.company.entities.GameEntity;
import com.company.entities.bullets.BulletMagazine;
import com.company.entities.human.Enemy;
import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.Resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public abstract class World implements Graphic, Serializable {
    public static String filename = "saves/world.data";
    protected final int width;
    protected final int height;
    protected final Room[] rooms;
    private final int WIDTH_IN_PIXEL;
    private final int HEIGHT_IN_PIXEL;
    protected byte roomId;
    protected byte textureFileName;

    protected Entity player;
    protected BulletMagazine magazine;
    protected java.util.Map<Byte, List<Entity>> enemies = new HashMap<>();

    public World(byte tfn, byte startRoom, Room[] r, Entity player, int tileWidth, int tileHeight) {
        this.textureFileName = tfn;
        this.rooms = r;
        this.roomId = startRoom;
        this.player = player;
        this.width = r[startRoom].tiles[0].length;
        this.height = r[startRoom].tiles.length;

        WIDTH_IN_PIXEL = width * tileWidth;
        HEIGHT_IN_PIXEL = height * tileHeight;

        magazine = new BulletMagazine();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        checkPlayerPosition();
        magazine.update(gl, dt);
    }

    public void collisions(GameEntity tmp) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tmp.handleCollisionWith(rooms[roomId].getTile(x, y));

        if (!(tmp instanceof Enemy)) {
            for (List<Entity> enemy : enemies.values())
                for (Entity e : enemy)
                    tmp.handleCollisionWith(e);
        } else {
            magazine.handleCollisionWith(tmp);
            tmp.handleCollisionWith(player);
        }

    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage tile = new TileImage(Resources.TEXTURES.get(textureFileName), -1, -1, GameLoop.TILE_WIDTH, GameLoop.TILE_HEIGHT);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tile.setOffX(x * GameLoop.TILE_WIDTH);
                tile.setOffY(y * GameLoop.TILE_HEIGHT);
                r.addImage(tile.getTile(roomId, rooms[roomId].tiles[y][x]));
            }
        }
        magazine.render(gl, r);
    }

    public int getWidthInPixel() {
        return WIDTH_IN_PIXEL;
    }

    public int getHeightInPixel() {
        return HEIGHT_IN_PIXEL;
    }

    public Tile getTile(int x, int y) {
        return rooms[roomId].getTile(x, y);
    }

    protected abstract void checkPlayerPosition();

    public byte getCurrentRoomId() {
        return roomId;
    }

    public List<Entity> getEnemies(byte id) {
        return enemies.get(id);
    }

    public int getNumberOfRooms() {
        return rooms.length;
    }
}
