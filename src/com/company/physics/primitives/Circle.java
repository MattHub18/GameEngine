package com.company.physics.primitives;

import com.company.physics.basics.Vector;
import com.company.physics.collisions.Collider;

public class Circle implements Collider {
    private final int radius;
    private final RigidBody body;

    public Circle(Vector center, int radius) {
        body = new RigidBody(center);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public Vector getCenter() {
        return body.getPosition();
    }
}
