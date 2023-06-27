package com.company.scenes;

import com.company.graphic.primitives.RenderObject;
import com.company.input.Controller;
import com.company.observer.Subject;

public interface Scene {
    RenderObject getRenderObject();

    Subject getActor();

    void stopSound();

    void connectController(Controller controller);

}
