package com.company.worlds;

import com.company.entities.EntityManager;
import com.company.entities.entity.GameEntity;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class Ambient implements Graphic {
    private final int roomId;
    private final byte textureFilename;
    public int LEFT;
    public int RIGHT;
    public int UP;
    public int DOWN;
    protected EntityManager entityManager;
    private int width;
    private int height;
    protected transient boolean visited;
    private byte[][] tiles;
    private int withInPixel;
    private int heightInPixel;

    public Ambient(byte textureFilename, int roomId, int shiftIndex) {
        this.roomId = roomId;
        this.textureFilename = textureFilename;
        this.entityManager = new EntityManager();
        visited = false;
        build(shiftIndex);
    }

    private void build(int shiftIndex) {
        String path = Archive.MAP.get(roomId + shiftIndex);
        File file = new File(path);
        try {
            Scanner sizeScanner = new Scanner(file);
            String[] temp = sizeScanner.nextLine().split(",");
            sizeScanner.close();
            this.width = temp.length;

            this.height = 0;
            try (Stream<String> lines = Files.lines(Path.of(path), Charset.defaultCharset())) {
                this.height = (int) lines.count();
            } catch (IOException e) {
                e.printStackTrace();
            }

            withInPixel = width * TILE_WIDTH;
            heightInPixel = height * TILE_HEIGHT;

            Scanner scanner = new Scanner(file);
            this.tiles = new byte[height][width];
            for (int i = 0; i < height; i++) {
                String[] numbers = scanner.nextLine().split(",");
                for (int j = 0; j < width; j++) {
                    this.tiles[i][j] = Byte.parseByte(numbers[j]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Tile getTile(int x, int y) {
        return new Tile(tiles[y][x], x, y);
    }

    @Override
    public void update(Engine engine, float dt) {
        entityManager.update(engine, dt);
        for (GameEntity e : entityManager.getEntities())
            tileCollision(e);
    }

    @Override
    public void render(Render r) {
        TileImage roomTextures = new TileImage(Archive.TEXTURES.get(textureFilename), TILE_WIDTH, TILE_HEIGHT, false, true);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                r.addImage(roomTextures.getTile(roomId, tiles[y][x]), x * TILE_WIDTH, y * TILE_HEIGHT);
            }
        }
        entityManager.render(r);
    }

    public int getWidthInPixel() {
        return withInPixel;
    }

    public int getHeightInPixel() {
        return heightInPixel;
    }

    public void tileCollision(GameEntity e) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile t = getTile(x, y);
                if (!t.isFloor())
                    e.handleCollisionWith(t.getBox());
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract void spawnEntities();

    public int getRoomId() {
        return roomId;
    }

    public void addPlayer(GameEntity player) {
        entityManager.addEntity(player);
    }

    public void removePlayer(GameEntity player) {
        entityManager.removeEntity(player);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
