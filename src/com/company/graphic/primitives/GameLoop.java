package com.company.graphic.primitives;

import com.company.commands.InputHandler;
import com.company.graphic.gfx.Font;
import com.company.states.StateManager;
import com.company.world.World;

public class GameLoop implements Runnable {
    public static float SCALE = 2f;

    protected static boolean running = false;

    private Thread gameThread;

    private final StateManager stateManager;

    private final Window window;
    private final Controller controller;
    private final Render render;
    private final InputHandler systemInputHandler;

    private boolean pause = false;

    public GameLoop(StateManager manager, InputHandler systemInputHandler, WindowHandler handler, World world, String title) {

        Camera camera = new Camera(world);
        window = new Window(camera, handler, title);
        controller = new Controller(window);
        render = new Render(camera, window);
        this.systemInputHandler = systemInputHandler;
        this.systemInputHandler.insertCommands();
        this.stateManager = manager;
        this.stateManager.init();
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

                systemInputHandler.handleInput(this, this);
                stateManager.head().update(this, (float) UPDATE_TIME);
                controller.update();

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (rendering) {
                render.clear();
                render.addFont(new Font("res/font/fps.png", "FPS: " + fps, 0, 0, 0xff0000ff));
                stateManager.head().render(this, render);
                render.process();
                window.update();
                frames++;
            }

        }
        stop();
    }

    public Controller getController() {
        return controller;
    }

    public void pause() {
        pause = !pause;
        if (!pause)
            stateManager.remove();
        else
            stateManager.insert("PAUSE");
    }

    public void changeWindowSize() {
        window.changeWindowSize();
    }
}
