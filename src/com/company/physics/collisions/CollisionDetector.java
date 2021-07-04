package com.company.physics.collisions;

import com.company.graphic.gfx.Rectangle;

public class CollisionDetector {

    public static boolean isCollided(Collider... colliders) {
        return !colliders[0].collision(colliders[1]);
    }

    public static Rectangle intersection(Collider... colliders) {
        return colliders[0].intersect(colliders[1]);
    }
}
