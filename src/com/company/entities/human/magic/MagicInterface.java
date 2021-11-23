package com.company.entities.human.magic;

import com.company.entities.bullet.MagicBullet;

public interface MagicInterface {
    void doMagic(MagicBullet magicBullet);

    int getMagicPoints();

    void setMagicPoints(int mp);

    int getMaxMagicPoints();

    boolean isDoingMagic();
}
