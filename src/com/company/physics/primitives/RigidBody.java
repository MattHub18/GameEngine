package com.company.physics.primitives;

import com.company.physics.basics.Vector;

public class RigidBody {
    private Vector position;

    public RigidBody(int posX, int posY) {
        this.position = new Vector(posX, posY);
    }

    public Vector getPosition() {
        return position;
    }
}
