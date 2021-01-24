package com.company.physics.primitives;

import com.company.physics.basics.Vector;

public class Circle {
    private int radius;
    private RigidBody body;

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
