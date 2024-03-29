package com.company.graphic.gfx.particles;

import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Explosion implements Graphic {
    private final List<Particle> particles;

    public Explosion(Particle... particles) {
        this.particles = new ArrayList<>(Arrays.asList(particles));
    }

    @Override
    public void update(Engine engine, float dt) {
        Iterator<Particle> itr = particles.iterator();
        while (itr.hasNext()) {
            Particle particle = itr.next();
            if (particle.isAlive())
                particle.update(engine, dt);
            else
                itr.remove();
        }

    }

    @Override
    public void render(Render r) {
        for (Particle p : particles)
            p.render(r);
    }
}
