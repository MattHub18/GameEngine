package com.company.entities.human.magic;

import com.company.entities.bullet.MagicBullet;
import com.company.entities.bullet.StaticBullet;
import com.company.entities.human.shooting.ShootingEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;

public class MagicEntity implements Graphic, MagicInterface, Serializable {
    private final int maxMagicPoints;
    private final ShootingEntity shootingEntity;
    private int magicPoints;

    public MagicEntity(ShootingEntity entity, int magicPoints) {
        this.shootingEntity = entity;
        this.magicPoints = magicPoints;
        this.maxMagicPoints = magicPoints;
    }

    @Override
    public int getMagicPoints() {
        return magicPoints;
    }

    @Override
    public void setMagicPoints(int mp) {
        magicPoints = mp;
    }

    @Override
    public int getMaxMagicPoints() {
        return maxMagicPoints;
    }

    @Override
    public void doMagic(MagicBullet magicBullet) {
        magicPoints -= magicBullet.getCost();
        if (magicPoints < 0) {
            magicPoints += magicBullet.getCost();
            return;
        }

        shootingEntity.addBullet((StaticBullet) magicBullet);
        shootingEntity.shooting();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (isDoingMagic())
            shootingEntity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (isDoingMagic())
            shootingEntity.render(gl, r);
    }

    @Override
    public boolean isDoingMagic() {
        return shootingEntity.isShooting();
    }
}
