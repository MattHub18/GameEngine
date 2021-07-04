package com.company.physics.forces;

import com.company.physics.basics.RigidBody;

import java.util.*;

public class ForceRegistry {
    private final Map<RigidBody, List<Force>> registry;

    public ForceRegistry() {
        registry = new HashMap<>();
    }

    public void add(RigidBody body, Force f) {
        if (registry.containsKey(body))
            registry.get(body).add(f);
        else
            registry.put(body, new ArrayList<>(Collections.singletonList(f)));
    }

    public void remove(RigidBody body, Force f) {
        if (registry.containsKey(body))
            registry.get(body).remove(f);
    }

    public void clear() {
        for (List<Force> forces : registry.values())
            forces.clear();
        registry.clear();
    }

    public void update(float dt) {
        for (Map.Entry<RigidBody, List<Force>> entry : registry.entrySet()) {
            RigidBody body = entry.getKey();
            for (Force f : entry.getValue())
                f.addForce(body);
            body.update(dt);
        }
    }
}
