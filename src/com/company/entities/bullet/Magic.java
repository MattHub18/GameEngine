package com.company.entities.bullet;

public class Magic implements MagicBullet {
    private final int cost;

    public Magic(int cost) {
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return cost;
    }
}
