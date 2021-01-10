package com.company.graphic;

import com.company.graphic.major.Render;

public interface Graphic {
    void update(GameLoop gl, float dt);

    void render(GameLoop gl, Render r);
}
