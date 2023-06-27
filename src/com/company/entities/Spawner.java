package com.company.entities;

import com.company.entities.entity.GameEntity;

public class Spawner {

    private final GameEntity prototype;
    private final int num;

    public Spawner(GameEntity prototype, int num) {
        this.prototype = prototype;
        this.num = num;
    }

    public GameEntity spawnMonster() {
        return prototype;
    }

    public int getNum() {
        return num;
    }
}
