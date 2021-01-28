package com.company.physics.forces;

import com.company.physics.primitives.RigidBody;

import java.util.ArrayList;
import java.util.List;

public class ForceRegistry {
    private List<ForceWrapper> registry;

    public ForceRegistry() {
        registry = new ArrayList<>();
    }

    public void add(Force f, RigidBody body) {
        registry.add(new ForceWrapper(f, body));
    }

    public void remove(Force f, RigidBody body) {
        registry.remove(new ForceWrapper(f, body));
    }

    public void clear() {
        for (ForceWrapper fr : registry) {
            fr.getBody().zeroForces();
        }
        registry.clear();
    }

    public void update(float dt) {
        for (ForceWrapper fr : registry) {
            RigidBody body = fr.getBody();
            fr.getForce().addForce(body);
            body.update(dt);
        }
    }
}
