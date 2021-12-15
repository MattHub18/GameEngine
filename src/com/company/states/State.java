package com.company.states;

import com.company.audio.Theme;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.RenderObject;
import com.company.observer.Subject;

public interface State extends Graphic, Theme {
    RenderObject getRenderObject();

    Subject getActor();
}
