package com.company.entities.bullet;

import java.io.Serializable;

public class Magic implements MagicBullet, Serializable {
    private final int cost;

    public Magic(int cost) {
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }
}
