package com.company.entities.bullet;

import com.company.directions.SystemFacingDirections;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.human.movable.AllDirectionalEntity;
import com.company.entities.human.movable.MovableInterface;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.collisions.CollisionDetector;
import com.company.world.Room;

import java.io.Serializable;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class Bullet implements Graphic, GameEntity, MovableInterface, Serializable {
    private final EntityGraphicComponent component;
    private final AllDirectionalEntity movableEntity;
    private final int damage;
    private Entity entity;
    private int maxTime;

    public Bullet(GameEntity shooter, EntityGraphicComponent component, int damage, int maxTime) {
        this.movableEntity = new AllDirectionalEntity(makeBullet(shooter), component);
        this.component = component;
        this.damage = damage;
        this.maxTime = maxTime;
    }

    private Bullet(Entity entity, AllDirectionalEntity movableEntity, EntityGraphicComponent component, int damage, int maxTime) {
        this.entity = entity;
        this.movableEntity = movableEntity;
        this.component = component;
        this.damage = damage;
        this.maxTime = maxTime;
    }

    private Entity makeBullet(GameEntity shooter) {
        byte facingDirection = shooter.getFacingDirection();
        int posX = shooter.getPosX();
        int posY = shooter.getPosY();

        if (facingDirection == SystemFacingDirections.NORTH)
            posY -= TILE_HEIGHT;
        else if (facingDirection == SystemFacingDirections.SOUTH)
            posY += TILE_HEIGHT;
        else if (facingDirection == SystemFacingDirections.WEST)
            posX -= TILE_WIDTH;
        else if (facingDirection == SystemFacingDirections.EAST)
            posX += TILE_WIDTH;

        this.entity = new Entity(posX, posY, facingDirection, shooter.getRoom(), null);
        return entity;
    }

    @Override
    public int getPosX() {
        return entity.getPosX();
    }

    @Override
    public void setPosX(int posX) {
        entity.setPosX(posX);
    }

    @Override
    public int getPosY() {
        return entity.getPosY();
    }

    @Override
    public void setPosY(int posY) {
        entity.setPosY(posY);
    }

    @Override
    public GameEntity copy() {
        return new Bullet((Entity) entity.copy(), (AllDirectionalEntity) movableEntity.copy(), component.copy(), damage, maxTime);
    }

    @Override
    public Room getRoom() {
        return entity.getRoom();
    }

    @Override
    public void setRoom(Room room) {
        entity.setRoom(room);
    }

    @Override
    public AxisAlignedBoundingBox getBox() {
        return entity.getBox();
    }

    @Override
    public byte getFacingDirection() {
        return entity.getFacingDirection();
    }

    @Override
    public void handleCollisionWith(AxisAlignedBoundingBox tileBox) {
        if (!CollisionDetector.isCollided(tileBox, entity.getBox()))
            return;
        maxTime = 0;
    }

    @Override
    public void moveUp() {
        movableEntity.moveUp();
    }

    @Override
    public void moveDown() {
        movableEntity.moveDown();
    }

    @Override
    public void moveLeft() {
        movableEntity.moveLeft();
    }

    @Override
    public void moveRight() {
        movableEntity.moveRight();
    }

    @Override
    public boolean isMoving() {
        return movableEntity.isMoving();
    }

    @Override
    public void clearMove() {
        movableEntity.clearMove();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        updateTime(dt);
        move();
        if (component != null)
            component.update(dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (component != null)
            component.render(r, entity.getPosX(), entity.getPosY(), entity.getFacingDirection());
    }

    private void updateTime(float dt) {
        maxTime -= dt * 15;
    }


    private void move() {
        byte facingDirection = entity.getFacingDirection();
        if (facingDirection == SystemFacingDirections.NORTH)
            moveUp();
        else if (facingDirection == SystemFacingDirections.SOUTH)
            moveDown();
        else if (facingDirection == SystemFacingDirections.WEST)
            moveLeft();
        else if (facingDirection == SystemFacingDirections.EAST)
            moveRight();
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getDamage() {
        return damage;
    }
}