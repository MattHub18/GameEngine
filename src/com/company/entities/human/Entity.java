package com.company.entities.human;

import com.company.directions.Direction;
import com.company.directions.FacingDirections;
import com.company.entities.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.CollisionDetector;
import com.company.resources.Resources;
import com.company.world.Tile;

import java.io.Serializable;
import java.util.Random;

public abstract class Entity implements Graphic, Serializable, GameEntity {
    public static String filename = "saves/player.data";

    protected int posX;
    protected int posY;

    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected Direction facing;

    protected float animationFrame;
    protected float animationDelay;
    protected byte facingDirection;
    protected Rectangle box;
    protected int maxFrames;
    protected byte textureFilename;
    private int uniqueId;

    public Entity(byte textureFilename, int tileWidth, int tileHeight, int posX, int posY, int maxF, float delay) {
        this.textureFilename = textureFilename;

        this.posX = posX * tileWidth;
        this.posY = posY * tileHeight;

        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

        this.facing = Direction.SOUTH;

        this.animationFrame = 0;
        this.animationDelay = delay;

        this.maxFrames = maxF;

        uniqueId = new Random().nextInt();

        box = new Rectangle(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT), 0xff000000, false);
    }

    public Entity copy(Entity copy) {
        this.facingDirection = copy.facingDirection;

        this.posX = copy.posX;
        this.posY = copy.posY;

        this.up = copy.up;
        this.down = copy.down;
        this.left = copy.left;
        this.right = copy.right;

        this.facing = copy.facing;

        this.animationFrame = copy.animationFrame;
        this.animationDelay = copy.animationDelay;

        this.box = copy.box;
        this.maxFrames = copy.maxFrames;
        this.textureFilename = copy.textureFilename;
        this.uniqueId = copy.uniqueId;
        return this;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void move() {
        if (up) {
            posY--;
            this.facing = Direction.NORTH;
            facingDirection = FacingDirections.BACK.value;
        }
        if (down) {
            posY++;
            this.facing = Direction.SOUTH;
            facingDirection = FacingDirections.FRONT.value;
        }
        if (left) {
            posX--;
            this.facing = Direction.WEST;
            facingDirection = FacingDirections.LEFT.value;
        }
        if (right) {
            posX++;
            this.facing = Direction.EAST;
            facingDirection = FacingDirections.RIGHT.value;
        }
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (up || down || left || right) {
            animationFrame += dt * animationDelay;
            if (animationFrame > maxFrames)
                animationFrame = 1;
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        TileImage player = new TileImage(Resources.TEXTURES.get(textureFilename), posX, posY, GameLoop.TILE_WIDTH, GameLoop.TILE_HEIGHT);
        if (up || down || left || right) {
            r.addImage(player.getTile(facingDirection, (int) animationFrame));
        } else {
            r.addImage(player.getTile(facingDirection, 0));
        }
    }

    public void handleCollisionWith(GameEntity e) {
        box = new Rectangle(new Vector(posX, posY), new Vector(posX + GameLoop.TILE_WIDTH, posY + GameLoop.TILE_HEIGHT), 0xff000000, false);
        Rectangle tileBox = e.getBox();

        if (CollisionDetector.isCollided(tileBox, box))
            return;

        if (e instanceof Tile)
            if (((Tile) e).isFloor())
                return;

        Rectangle intersection = CollisionDetector.intersection(box, tileBox);

        if (intersection != null) {
            if (intersection.getWidth() > intersection.getHeight()) {

                if (posY < tileBox.getMin().getY())
                    posY = (int) (tileBox.getMin().getY() - box.getHeight());
                else
                    posY = (int) (tileBox.getMin().getY() + box.getHeight());
            } else {
                if (posX < tileBox.getMin().getX())
                    posX = (int) (tileBox.getMin().getX() - box.getWidth());
                else
                    posX = (int) (tileBox.getMin().getX() + box.getWidth());
            }
        }
    }

    public Direction getFacing() {
        return facing;
    }

    public void receiveDamage(int damage) {
        throw new RuntimeException("OVERRIDE IT");
    }
}
