package com.company.entities.human.magic;

import com.company.entities.bullet.Bullet;
import com.company.entities.bullet.MagicBullet;
import com.company.entities.human.entity.Entity;
import com.company.entities.human.entity.EntityGraphicComponent;
import com.company.entities.human.entity.GameEntity;
import com.company.entities.human.healer.HealProperty;
import com.company.entities.human.shooting.ShootingEntity;

import java.io.Serializable;

public class MagicEntity extends ShootingEntity implements MagicInterface, HealProperty, Serializable {
    private final int maxMagicPoints;
    private int magicPoints;

    public MagicEntity(Entity entity, EntityGraphicComponent component, int magicPoints) {
        super(entity, component);
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

        addBullet((Bullet) magicBullet);
        shooting();
    }

    @Override
    public boolean isDoingMagic() {
        return isShooting();
    }

    @Override
    public int getHealProperty() {
        return getMagicPoints();
    }

    @Override
    public void setHealProperty(int value) {
        setMagicPoints(value);
    }

    @Override
    public int getMaxHealProperty() {
        return getMaxMagicPoints();
    }

    @Override
    public GameEntity copy() {
        return new MagicEntity((Entity) entity.copy(), component.copy(), magicPoints);
    }
}
