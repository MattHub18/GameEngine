package com.company.physics.collisions;

import com.company.graphic.gfx.Rectangle;
import com.company.physics.basics.Vector;

public interface Collider {
    Vector getCenter();

    boolean collision(Collider collider);

    Rectangle intersect(Collider collider);
}
