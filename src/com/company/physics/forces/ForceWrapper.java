package com.company.physics.forces;

import com.company.physics.primitives.RigidBody;

public class ForceWrapper {
    private Force f;
    private RigidBody body;

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
