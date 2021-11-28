package com.company.states;

import com.company.graphic.Graphic;
import com.company.graphic.primitives.RenderObject;
import com.company.observer.Subject;

public interface State extends Graphic {
    RenderObject getRenderObject();

    Subject getActor();
}
