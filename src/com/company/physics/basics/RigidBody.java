package com.company.physics.basics;

public class RigidBody {
    private final Vector position;
    private final Vector resultantForce;
    private int mass;

    public RigidBody(Vector center) {
        this.position = new Vector(center);
        this.mass = 0;
        this.resultantForce = new Vector(0, 0);
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public Vector getPosition() {
        return position;
    }

    public void addForce(Vector force) {
        this.resultantForce.add(force);
    }

    public void update(float dt) {
        if (this.mass == 0.0f)
            return;
        linearKinematics(dt);
    }

    private void linearKinematics(float dt) {
        Vector acceleration = new Vector(resultantForce).mul(1f / mass);
        Vector linearVelocity = new Vector(acceleration.mul(dt));
        this.position.add(new Vector(linearVelocity).mul(dt));
    }
}
