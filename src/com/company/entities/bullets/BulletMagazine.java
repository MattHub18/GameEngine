package com.company.entities.bullets;

import com.company.entities.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.worlds.Map;
import com.company.worlds.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletMagazine implements Graphic, Serializable {

    private List<Bullet> magazine;
    private Entity player;
    private Map map;

    public BulletMagazine(Entity p, Map m) {
        player = p;
        map = m;
        magazine = new ArrayList<>();
    }

    @Override
    public void update(GameLoop gl, float dt) {
        Iterator<Bullet> b = magazine.iterator();
        while (b.hasNext()) {
            Bullet tmp = b.next();
            if (tmp.getMaxRange() > 0 && !collisions(tmp))
                tmp.update(gl, dt);
            else
                b.remove();

        }
    }

    private boolean collisions(Bullet tmp) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tile t = map.getTile(x, y);
                if (tmp.handleCollisionWith(t))
                    return true;
            }
        }
        return false;
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
}

