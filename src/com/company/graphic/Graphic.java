package com.company.graphic;

import com.company.graphic.primitives.Render;

public interface Graphic {
    void update(Engine engine, float dt);

    void render(Render r);
}
