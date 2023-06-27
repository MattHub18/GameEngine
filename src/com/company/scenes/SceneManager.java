package com.company.scenes;

import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.Render;
import com.company.input.Controller;

public abstract class SceneManager {

    protected Scene currentScene;
    protected Scene gameScene;

    public SceneManager() {
        currentScene = null;
        gameScene = null;
    }

    protected abstract Scene init();

    protected abstract Scene createNewState(String state);

    public void nextState(String state) {
        currentScene = state == null ? init() : createNewState(state);
    }

    public Scene getCurrentState() {
        return currentScene;
    }

    public void connectController(Controller controller) {
        currentScene.connectController(controller);
    }

    public void stopSound() {
        if (currentScene != null)
            currentScene.stopSound();
        if (gameScene != null)
            gameScene.stopSound();
    }

    public void update(Engine engine, float dt) {
        ((Graphic) currentScene).update(engine, dt);
    }

    public void render(Render render) {
        ((Graphic) currentScene).render(render);
    }

    public Scene getGameState() {
        return gameScene;
    }
}
