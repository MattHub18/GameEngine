package com.company.graphic.gfx;

import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
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
    public void update(GameLoop gl, float dt) {
        Iterator<Particle> itr = particles.iterator();
        while (itr.hasNext()) {
            Particle particle = itr.next();
            if (particle.isAlive())
                particle.update(gl, dt);
            else
                itr.remove();
        }

    }

    @Override
    public void render(GameLoop gl, Render r) {
        for (Particle p : particles)
            p.render(gl, r);
    }
}
