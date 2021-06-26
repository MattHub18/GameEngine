package com.company.graphic.primitives;

import com.company.entities.human.Entity;
import com.company.worlds.Map;

public interface Registration {
    String message = "You can't call this method";

    default Entity registerEntityToCamera() {
        throw new UnsupportedOperationException(message);
    }

    default Map registerMapToCamera() {
        throw new UnsupportedOperationException(message);
    }

    default void registerEnemyToHud(Entity enemy) {
        throw new UnsupportedOperationException(message);
    }
}
