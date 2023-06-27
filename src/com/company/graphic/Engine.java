package com.company.graphic;

import com.company.entities.entity.GameEntity;
import com.company.event.Event;
import com.company.graphic.gfx.Font;
import com.company.graphic.primitives.*;
import com.company.input.Controller;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;
import com.company.resources.file_system.FileSystem;
import com.company.scenes.Scene;
import com.company.scenes.SceneManager;

public class Engine implements Runnable {
    private final SceneManager sceneManager;

    private final FileSystem fileSystem;
    private final Window window;
    private final Controller controller;
    private final Render render;
    private final Camera camera;
    private boolean running = false;
    private Thread gameThread;

    public Engine(FileSystem fileSystem, SceneManager manager, String title) {
        this.fileSystem = fileSystem;
        this.fileSystem.loadResources();
        this.window = new Window(title);
        this.camera = new Camera(window);

        this.controller = new Controller(window);
        this.render = new Render(camera, window);

        this.sceneManager = manager;
        nextState(null);
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
        sceneManager.stopSound();
        window.close();
        render.clear();
        fileSystem.shutdown();
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

        window.registerEngine(this);

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

                sceneManager.update(this, (float) UPDATE_TIME);
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
                sceneManager.render(render);
                render.process();
                window.update();
                frames++;
            }
        }
    }

    public void nextState(String state) {
        Scene current = sceneManager.getCurrentState();
        if (current != null)
            camera.unregisterEntityToObserver(current.getActor());
        sceneManager.nextState(state);
        current = sceneManager.getCurrentState();
        updateCamera(current.getRenderObject());
        camera.registerEntityToObserver(current.getActor());
        sceneManager.connectController(controller);
    }

    public void changeWindowSize() {
        window.changeWindowSize();
    }


    public void updateCamera(RenderObject obj) {
        camera.updateCamera(obj);
    }

    public void save() {
        GameEntity entity = ((GameEntity) sceneManager.getGameState().getActor());
        String name = entity.getClass().getSimpleName();
        Event.updateEvent(name);
        fileSystem.save(name, entity.serialize());
    }

    public void loadSaving() {
        nextState((String) this.fileSystem.get(".data"));
    }
}