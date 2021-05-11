package com.company.graphic.primitives;

import com.company.graphic.Graphic;

import java.awt.event.KeyEvent;

public class GameLoop implements Runnable {
    public static final int WIDTH = 320;
    public static final int HEIGHT = 180;
    public static final float SCALE = 2f;

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    private String title = "GameEngine2D v0.0";
    private boolean running = false;

    private Thread gameThread;

    private final Graphic game;

    private final Window window;
    private final Controller controller;
    private final Render render;

    private boolean pause = false;

    public GameLoop(Graphic game) {
        window = new Window(this);
        controller = new Controller(this);
        render = new Render(this);
        this.game = game;
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
        double UPDATE_TIME = 1.0 / 60.0;
        double frameTime = 0;
        int frames = 0;
        int fps;

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

                if (controller.isKey(KeyEvent.VK_P))
                    pause = !pause;
                if (!pause)
                    game.update(this, (float) UPDATE_TIME);
                controller.update();

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    System.out.println("FPS: " + fps);
                    frames = 0;
                }
            }

            if (rendering) {
                render.clear();
                game.render(this, render);
                render.process();
                window.update();
                frames++;
            }

        }
        stop();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Window getWindow() {
        return window;
    }

    public Controller getController() {
        return controller;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
