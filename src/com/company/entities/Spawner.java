package com.company.entities;

import com.company.entities.human.GameEntity;

public class Spawner {

    private final GameEntity prototype;
    private final int num;

    public Spawner(GameEntity prototype, int num) {
        this.prototype = prototype;
        this.num = num;
    }

    public GameEntity spawnMonster() {
        return prototype.copy();
    }

    public int getNum() {
        return num;
    }
}
