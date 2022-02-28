package com.company.entities.human.shooting;

import com.company.entities.EntityManager;
import com.company.entities.bullet.StaticBullet;
import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.entities.human.combat.CombatInterface;
import com.company.entities.human.combat.Damageable;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.collisions.CollisionDetector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ShootingEntity implements Graphic, ShootingInterface, Serializable {

    private final Entity entity;
    private final ArrayList<StaticBullet> currentQueue;
    private boolean shooting;

    public ShootingEntity(Entity entity) {
        this.entity = entity;
        this.shooting = false;
        currentQueue = new ArrayList<>();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        Iterator<StaticBullet> iterator = currentQueue.iterator();
        while (iterator.hasNext()) {
            StaticBullet bullet = iterator.next();
            if (bullet.getMaxTime() > 0) {
                bullet.update(gl, dt);
                entity.getRoom().tileCollision(bullet);
                entity.getRoom().getEntityManager().entityCollision(bullet);
                if (handleCollisionWithEntity(bullet))
                    remove(iterator);
            } else
                remove(iterator);
        }

        entity.update(gl, dt);
    }

    private void remove(Iterator<StaticBullet> iterator) {
        iterator.remove();
        shooting = false;
    }

    private boolean handleCollisionWithEntity(StaticBullet bullet) {
        EntityManager entityManager = entity.getRoom().getEntityManager();
        Iterator<GameEntity> enemyIterator = entityManager.getEntities().iterator();
        while (enemyIterator.hasNext()) {
            GameEntity enemy = enemyIterator.next();

                if (enemy instanceof Damageable) {
                    if (handleAttackCollision(enemy, bullet)) {
                        ((Damageable) enemy).receiveDamage(enemy, bullet.getDamage());
                        if (((CombatInterface) enemy).isDead()) {
                            enemyIterator.remove();
                            return true;
                        }
                    }
                }
        }
        return false;
    }

    private boolean handleAttackCollision(GameEntity enemy, StaticBullet bullet) {
        AxisAlignedBoundingBox attack = bullet.getBox();
        AxisAlignedBoundingBox enemyBox = enemy.getBox();
        return CollisionDetector.isCollided(attack, enemyBox);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (StaticBullet bullet : currentQueue)
            bullet.render(gl, r);
        entity.render(gl, r);
    }


    @Override
    public void shooting() {
        shooting = true;
    }

    public void addBullet(StaticBullet bullet) {
        currentQueue.add(bullet);
    }

    @Override
    public boolean isShooting() {
        return shooting;
    }
}
