package com.company.physics.collisions;

import com.company.physics.basics.AxisAlignedBoundingBox;
import com.company.physics.basics.Vector;

public interface Collider {
    Vector getCenter();

    boolean collision(Collider collider);

    AxisAlignedBoundingBox intersect(Collider collider);
}
