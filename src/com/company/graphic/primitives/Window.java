package com.company.graphic.primitives;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    public static int WIDTH;
    public static int HEIGHT;
    private final BufferedImage image;
    private final Canvas canvas;
    private final JFrame frame;
    private final WindowHandler handler;
    private BufferStrategy bs;
    private Graphics g;

    public Window(Camera camera, WindowHandler handler, String title) {
        image = new BufferedImage(camera.getMapWidthInPixel(), camera.getMapHeightInPixel(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();

        frame = new JFrame(title);
        this.handler = handler;
        handler.setFrame(frame);

        changeWindowSize(true);
    }

    public void update() {
        g.drawImage(image, 0, 0, (int) (image.getWidth() * GameLoop.SCALE), (int) (image.getHeight() * GameLoop.SCALE), null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void changeWindowSize(boolean isFullScreen) {
        frame.dispose();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        if (isFullScreen) {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            GameLoop.SCALE = 2f;
            Dimension fullDim = Toolkit.getDefaultToolkit().getScreenSize();
            WIDTH = (int) (fullDim.width / GameLoop.SCALE);
            HEIGHT = (int) (fullDim.height / GameLoop.SCALE);
        } else {
            frame.setUndecorated(false);
            device.setFullScreenWindow(null);
            GameLoop.SCALE = 1f;
            WIDTH = 640;
            HEIGHT = 360;
        }
        Dimension dim = new Dimension((int) (WIDTH * GameLoop.SCALE), (int) (HEIGHT * GameLoop.SCALE));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        frame.addWindowListener(handler);
        frame.setLayout(new BorderLayout());

        frame.add(canvas, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }
}
