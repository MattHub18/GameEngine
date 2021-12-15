package com.company.states;

import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.menu.Menu;
import com.company.observer.Subject;

public class MenuState implements State {

    private final Menu menu;

    public MenuState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        menu.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        menu.render(gl, r);
    }

    @Override
    public RenderObject getRenderObject() {
        return menu;
    }

    @Override
    public Subject getActor() {
        return null;
    }

    @Override
    public void stopSound() {
        menu.stopSound();
    }

    public Class<? extends Menu> instanceOf() {
        return menu.getClass();
    }
}
