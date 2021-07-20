package com.company.entities.bullets;

import com.company.entities.GameEntity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletMagazine implements Graphic, Serializable, GameEntity {

    private final List<Bullet> magazine;

    public BulletMagazine() {
        this.magazine = new ArrayList<>();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        Iterator<Bullet> b = magazine.iterator();
        while (b.hasNext()) {
            Bullet tmp = b.next();
            if (tmp.getMaxRange() > 0)
                tmp.update(gl, dt);
            else
                b.remove();
        }
    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (Bullet b : magazine) {
            b.render(gl, r);
        }
    }

    public void add(Bullet bullet) {
        magazine.add(bullet);
    }

    @Override
    public void handleCollisionWith(GameEntity e) {
        for (Bullet b : magazine) {
            b.handleCollisionWith(e);
        }
    }
}

