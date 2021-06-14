package com.company.entities.human;

import com.company.entities.bullets.Bullet;
import com.company.entities.bullets.BulletMagazine;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.Resources;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Player extends Entity implements Serializable {

    public static String filename = "saves/player.data";
    private final int maxFrames;
    private final BulletMagazine magazine;
    private boolean shooting;

    public Player(int mf) {
        super(Resources.PLAYER, 1, 1, 15);
        this.maxFrames = mf;
        shooting = false;
        magazine = new BulletMagazine();
    }

    public Player(Player copy) {
        super(copy);
        this.maxFrames = copy.maxFrames;
        this.shooting = copy.shooting;
        this.magazine = copy.magazine;
    }

    public BulletMagazine getMagazine() {
        return magazine;
    }

    @Override
    public void move() {
        super.move();
        switch (super.facing) {
            case NORTH:
                super.entityID = Resources.PLAYER_BACK;
                break;
            case SOUTH:
                super.entityID = Resources.PLAYER_FRONT;
                break;
            case WEST:
                super.entityID = Resources.PLAYER_LEFT;
                break;
            case EAST:
                super.entityID = Resources.PLAYER_RIGHT;
                break;
        }
    }

    public void shooting(GameLoop gl, Bullet bullet) {
        shooting = gl.getController().isKeyDown(KeyEvent.VK_SPACE);
        if (shooting)
            magazine.add(bullet);
    }

    public void checkMovement(GameLoop gl) {
        up = gl.getController().isKey(KeyEvent.VK_W);
        left = gl.getController().isKey(KeyEvent.VK_A);
        down = gl.getController().isKey(KeyEvent.VK_S);
        right = gl.getController().isKey(KeyEvent.VK_D);
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (up || down || left || right) {
            animationFrame += dt * animationDelay;
            if (animationFrame > maxFrames)
                animationFrame = 1;
        }
        magazine.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage player = new TileImage(Resources.TEXTURES.get(Resources.PLAYER), TILE_WIDTH, TILE_HEIGHT);
        if (up || down || left || right) {
            r.addImage(player.getTile((int) animationFrame, entityID), super.posX, super.posY);
        } else {
            r.addImage(player.getTile(0, entityID), super.posX, super.posY);
        }
    }
}