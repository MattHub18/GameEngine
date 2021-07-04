package com.company.graphic.primitives;

import com.company.entities.human.Entity;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.world.World;

import java.awt.event.KeyEvent;

public class GameLoop implements Runnable {
    public static float SCALE = 2f;

    public static int TILE_WIDTH;
    public static int TILE_HEIGHT;

    protected static boolean running = false;

    private Thread gameThread;

    private final Graphic game;

    private final Window window;
    private final Controller controller;
    private final Render render;

    private boolean pause = false;
    private boolean fullScreen = true;

    public GameLoop(Graphic game, WindowHandler handler, Entity player, World world, int tileWidth, int tileHeight, String title) {
        TILE_WIDTH = tileWidth;
        TILE_HEIGHT = tileHeight;

        Camera camera = new Camera(player, world);
        window = new Window(camera, handler, title);
        controller = new Controller(window);
        render = new Render(camera, window);
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
            if (controller.isKeyDown(KeyEvent.VK_F11)) {
                fullScreen = !fullScreen;
                window.changeWindowSize(fullScreen);
            }

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
                    render.setFpsFont(new Font("res/font/fps.png", "FPS: " + fps, 0, 0, 0xff0000ff));
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
    public Controller getController() {
        return controller;
    }
}
