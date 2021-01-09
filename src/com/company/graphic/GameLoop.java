package com.company.graphic;

import com.company.graphic.major.Controller;
import com.company.graphic.major.Window;

public class GameLoop implements Runnable {
    public static final int WIDTH = 160;
    public static final int HEIGHT = 90;
    public static final float SCALE = 1f;
    private String title = "GameEngine2D v0.0";
    private boolean running = false;

    private Thread gameThread;

    private Window window;
    private Controller controller;

    public GameLoop() {

    }

    public synchronized void start() {
        if (running)
            return;

        window = new Window(this);

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

        boolean render;
        double startTime;
        double finishTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double remainingTime = 0;
        double UPDATE_TIME = 1.0 / 60.0;
        double frameTime = 0;
        int frames = 0;
        int fps;

        while (running) {
            render = true;

            startTime = System.nanoTime() / 1000000000.0;
            passedTime = startTime - finishTime;
            finishTime = startTime;
            remainingTime += passedTime;
            frameTime += passedTime;

            while (remainingTime >= UPDATE_TIME) {
                remainingTime -= UPDATE_TIME;
                render = false;

                //TODO:UPDATE

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    System.out.println("FPS: " + fps);
                    frames = 0;
                }
            }

            if (render) {
                //TODO:RENDER
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
}
