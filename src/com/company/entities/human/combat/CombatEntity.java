package com.company.entities.human.combat;

import com.company.death_strategy.Death;
import com.company.entities.EntityManager;
import com.company.entities.human.Entity;
import com.company.entities.human.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.collisions.CollisionDetector;
import com.company.weapons.Weapon;

import java.io.Serializable;
import java.util.Iterator;

import static com.company.directions.SystemFacingDirections.TOTAL_DIRECTION;

public class CombatEntity implements Graphic, CombatInterface, Damageable, Serializable {

    protected final Weapon weapon;
    private final int maxLifePoints;
    private final Death deathStrategy;
    private final Entity entity;
    protected int lifePoints;
    private boolean attack;
    private boolean dead;

    public CombatEntity(Entity entity, int lifePoints, Weapon weapon, Death deathStrategy) {
        this.entity = entity;
        this.attack = false;
        this.lifePoints = lifePoints;
        this.weapon = weapon;
        this.deathStrategy = deathStrategy;
        this.maxLifePoints = lifePoints;
        this.dead = false;
    }

    @Override
    public boolean isAttack() {
        return attack;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
        if (entity.getAnimationFrame() == 0f)
            attack = false;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        byte amount = TOTAL_DIRECTION;
        amount += TOTAL_DIRECTION;
        entity.incrementFacingDirection(amount);
        entity.render(gl, r);
        entity.decrementFacingDirection(amount);
    }

    @Override
    public int getLifePoints() {
        return lifePoints;
    }

    @Override
    public void setLifePoints(int lp) {
        this.lifePoints = lp;
    }

    @Override
    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    @Override
    public void meleeAttack() {
        attack = true;

        EntityManager entityManager = entity.getRoom().getEntityManager();
        Iterator<GameEntity> enemyIterator = entityManager.getEntities().iterator();
        while (enemyIterator.hasNext()) {
            GameEntity enemy = enemyIterator.next();
                if (enemy instanceof Damageable) {
                    if (handleAttackCollision(enemy)) {
                        doDamage(enemy, enemyIterator);
                    }
                }
        }
    }

    private boolean handleAttackCollision(GameEntity enemy) {
        AxisAlignedBoundingBox attack = weapon.getBox(entity);
        entity.updateBox();
        AxisAlignedBoundingBox enemyBox = enemy.getBox();
        return CollisionDetector.isCollided(attack, enemyBox);
    }


    protected void doDamage(GameEntity enemy, Iterator<GameEntity> enemyIterator) {
        ((Damageable) enemy).receiveDamage(enemy, weapon.getDamage());
        if (((CombatInterface) enemy).isDead())
            enemyIterator.remove();
    }


    @Override
    public void receiveDamage(GameEntity entity, int damage) {
        lifePoints -= damage;
        if (lifePoints <= 0)
            deathStrategy.die(entity);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
