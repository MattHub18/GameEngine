package com.company.physics.primitives;

import com.company.physics.basics.Vector;

public class RigidBody {
    private Vector position;

    public RigidBody(Vector center) {
        this.position = new Vector(center);
    }

    public Vector getPosition() {
        return position;
    }
}
