package com.company.graphic;

import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

public interface Graphic {
    void update(GameLoop gl, float dt);

    void render(GameLoop gl, Render r);
}
