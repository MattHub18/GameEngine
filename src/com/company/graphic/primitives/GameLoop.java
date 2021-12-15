package com.company.graphic.primitives;

import com.company.commands.InputHandler;
import com.company.graphic.gfx.Font;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;
import com.company.states.State;
import com.company.states.StateManager;

public class GameLoop implements Runnable {
    protected static float SCALE = 2f;
    private final StateManager stateManager;
    private final Window window;
    private final Controller controller;
    private final Render render;
    private final InputHandler systemInputHandler;
    private final Camera camera;
    private boolean running = false;
    private Thread gameThread;

    public GameLoop(StateManager manager, InputHandler systemInputHandler, String title) {
        this.stateManager = manager;
        this.stateManager.init();

        this.window = new Window(title);
        this.camera = new Camera();

        update();

        this.controller = new Controller(window);
        this.render = new Render(camera, window);

        this.systemInputHandler = systemInputHandler;
        this.systemInputHandler.insertCommands();
    }

    public synchronized void start() {
        if (running)
            return;

        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        stateManager.getCurrentState().stopSound();
        window.close();
        stateManager.clear();
        render.clear();
        gameThread.interrupt();
    }

    @Override
    public void run() {
        running = true;

        boolean rendering;
        double startTime;
        double finishTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double remainingTime = 0;
        double UPDATE_TIME = 1.0 / 90.0;
        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (running) {
            rendering = true;

            startTime = System.nanoTime() / 1000000000.0;
            passedTime = startTime - finishTime;
            finishTime = startTime;
            remainingTime += passedTime;
            frameTime += passedTime;

            while (remainingTime >= UPDATE_TIME) {
                remainingTime -= UPDATE_TIME;
                rendering = false;

                systemInputHandler.handleInput(this);
                stateManager.getCurrentState().update(this, (float) UPDATE_TIME);
                controller.update();

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (rendering) {
                render.clear();
                render.addFont(new Font(Archive.FONT.get(SystemResources.FPS_FONT), "FPS: " + fps, 0, 0, ColorPalette.BLUE, true));
                stateManager.getCurrentState().render(this, render);
                render.process();
                window.update(this);
                frames++;
            }
        }
    }

    public void nextState(String state) {
        camera.unregisterEntityToObserver(stateManager.getCurrentState().getActor());
        stateManager.nextState(state);

        update();

        render.updateBuffer(window);
    }

    public void changeWindowSize() {
        window.changeWindowSize();
    }

    private void update() {
        State current = stateManager.getCurrentState();
        RenderObject obj = current.getRenderObject();
        window.updateBuffer(obj);
        camera.updateCamera(obj);
        camera.registerEntityToObserver(current.getActor());
    }
}
