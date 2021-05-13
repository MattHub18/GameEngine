package com.company.physics.primitives;

import com.company.physics.basics.Vector;
import com.company.physics.collisions.Collider;

import java.io.Serializable;

public class AxisAlignedBoundingBox implements Collider, Serializable {
    private final Vector halfSize;
    private final RigidBody body;

    public AxisAlignedBoundingBox(Vector min, Vector max) {
        Vector size = new Vector(max).sub(min);
        this.halfSize = new Vector(size).mul(0.5f);
        body = new RigidBody(new Vector(halfSize).add(min));
    }

    public Vector getMin() {
        return new Vector(this.body.getPosition()).sub(this.halfSize);
    }

    public Vector getMax() {
        return new Vector(this.body.getPosition()).add(this.halfSize);
    }

    @Override
    public Vector getCenter() {
        return new Vector(this.body.getPosition());
    }

    public float getWidth() {
        return getMax().sub(getMin()).getX();
    }

    public float getHeight() {
        return getMax().sub(getMin()).getY();
    }
}
