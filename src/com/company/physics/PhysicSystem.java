package com.company.physics;

import com.company.physics.basics.RigidBody;
import com.company.physics.forces.Force;
import com.company.physics.forces.ForceRegistry;

public interface PhysicSystem {

    ForceRegistry registry = new ForceRegistry();

    default void addForce(RigidBody body, Force f) {
        registry.add(body, f);
    }

    default void removeForce(RigidBody body, Force f) {
        registry.remove(body, f);
    }

    default void clearSystem() {
        registry.clear();
    }

    default void updateSystem(float dt) {
        registry.update(dt);
    }

}
