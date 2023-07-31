package com.company.entities;

import com.company.entities.entity.GameEntity;
import com.company.util.Prototype;

public class Spawner {

    private final Prototype prototype;
    private final int num;

    public Spawner(Prototype prototype, int num) {
        this.prototype = prototype;
        this.num = num;
    }

    public GameEntity spawnMonster() {
        return (GameEntity) prototype.copy();
    }

    public int getNum() {
        return num;
    }
}
