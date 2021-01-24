package com.company.physics.primitives;

import com.company.physics.basics.Vector;

public class AxisAlignedBoundingBox {
    private Vector size;
    private Vector halfSize;
    private RigidBody body;

    public AxisAlignedBoundingBox(Vector min, Vector max) {
        this.size = new Vector(max).sub(min);
        this.halfSize = new Vector(size).mul(0.5f);
        body = new RigidBody(halfSize);
    }

    public Vector getMin() {
        return new Vector(this.body.getPosition()).sub(this.halfSize);
    }

    public Vector getMax() {
        return new Vector(this.body.getPosition()).add(this.halfSize);
    }
}
