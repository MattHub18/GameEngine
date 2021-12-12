package com.company.world;

import com.company.entities.EntityManager;
import com.company.entities.human.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public abstract class Room implements Graphic, Serializable {
    private final int roomId;
    public int LEFT;
    public int RIGHT;
    public int UP;
    public int DOWN;
    private final int shiftIndex;
    protected EntityManager entityManager;
    private int width;
    private int height;
    protected transient boolean visited;
    private byte[][] tiles;
    private int withInPixel;
    private int heightInPixel;
    private final byte textureFilename;

    public Room(byte textureFilename, int roomId, int shiftIndex, EntityManager entityManager) {
        this.roomId = roomId;
        this.textureFilename = textureFilename;
        this.shiftIndex = shiftIndex;
        this.entityManager = entityManager;
        visited = false;
        build();
    }

    protected void build() {
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

            withInPixel = width * TILE_WIDTH();
            heightInPixel = height * TILE_HEIGHT();

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
    public void update(GameLoop gl, float dt) {
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage roomTextures = new TileImage(Archive.TEXTURES.get(textureFilename), TILE_WIDTH(), TILE_HEIGHT(), false, true);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                r.addImage(roomTextures.getTile(roomId, tiles[y][x]), x * TILE_WIDTH(), y * TILE_HEIGHT());
            }
        }
    }

    public int getWidthInPixel() {
        return withInPixel;
    }

    public int getHeightInPixel() {
        return heightInPixel;
    }

    public void collisions(GameEntity e) {
        tileCollision(e);
        entityManager.entityCollision(e);
    }

    private void tileCollision(GameEntity e) {
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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
