package com.company.physics.forces;

import com.company.physics.primitives.RigidBody;

public class ForceWrapper {
    private final Force f;
    private final RigidBody body;

    public ForceWrapper(Force f, RigidBody body) {
        this.f = f;
        this.body = body;
    }

    public Force getForce() {
        return f;
    }

    public RigidBody getBody() {
        return body;
    }
}
