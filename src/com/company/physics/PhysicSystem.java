package com.company.physics;

import com.company.physics.forces.Force;
import com.company.physics.forces.ForceRegistry;
import com.company.physics.primitives.RigidBody;

public class PhysicSystem {

    private ForceRegistry registry;

    public PhysicSystem() {
        registry = new ForceRegistry();
    }

    public void addForce(Force f, RigidBody body) {
        registry.add(f, body);
    }

    public void removeForce(Force f, RigidBody body) {
        registry.remove(f, body);
    }

    public void clearSystem() {
        registry.clear();
    }

    public void updateSystem(float dt) {
        registry.update(dt);
    }

}
