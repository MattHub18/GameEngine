package com.company.graphic.primitives;

import com.company.entities.human.Entity;
import com.company.worlds.Map;

public interface CameraRegistration {
    Entity registerInitialEntity();

    Map registerInitialMap();
}
