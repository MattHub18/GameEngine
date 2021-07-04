package com.company.physics.forces;

import com.company.physics.basics.RigidBody;
import com.company.physics.basics.Vector;

public class Gravity implements Force {
    private static final int G = 10;

    @Override
    public void addForce(RigidBody body) {
        body.addForce(new Vector(0, G * body.getMass()));
    }
}
