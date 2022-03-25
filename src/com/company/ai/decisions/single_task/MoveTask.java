package com.company.ai.decisions.single_task;

import com.company.ai.AiInterface;
import com.company.ai.movement.MovementComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.human.movable.MovableInterface;
import com.company.physics.basics.Point;

import static com.company.resources.SystemConstants.TILE_HEIGHT;
import static com.company.resources.SystemConstants.TILE_WIDTH;

public class MoveTask extends LeafTask {
    private final MovementComponent movementComponent;
    private Point target;

    public MoveTask(MovementComponent movementComponent) {
        this.movementComponent = movementComponent;
    }

    @Override
    public boolean checkConditions(AiInterface entity) {
        return target != null;
    }

    @Override
    public void doAction(GameEntity entity) {
        Point move = movementComponent.move(entity, target.getX(), target.getY());

        if (move == null)
            return;

        if (move.getX() != entity.getPosX() / TILE_WIDTH) {
            if (move.getX() > entity.getPosX() / TILE_WIDTH)
                ((MovableInterface) entity).moveRight();
            else
                ((MovableInterface) entity).moveLeft();
        }
        if (move.getY() != entity.getPosY() / TILE_HEIGHT) {
            if (move.getY() > entity.getPosY() / TILE_HEIGHT)
                ((MovableInterface) entity).moveDown();
            else
                ((MovableInterface) entity).moveUp();
        }

        control.finishWithSuccess();
    }

    @Override
    public double getValue(AiInterface entity) {
        target = entity.chooseMoveTarget();
        if (target != null)
            return (double) Math.abs(((GameEntity) entity).getPosX() - target.getX()) / TILE_WIDTH + (double) Math.abs(((GameEntity) entity).getPosY() - target.getY()) / TILE_HEIGHT;//min val = 2 o 1;
        return -1;
    }
}
