package com.company.graphic.primitives;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    public static int WIDTH;
    public static int HEIGHT;

    private final BufferedImage image;
    private final Canvas canvas;
    private final JFrame frame;
    private BufferStrategy bs;
    private Graphics g;

    private boolean fullScreen = true;

    public Window(String title) {
        Dimension fullDim = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) (fullDim.width / GameLoop.SCALE);
        HEIGHT = (int) (fullDim.height / GameLoop.SCALE);

        canvas = new Canvas();

        frame = new JFrame(title);

        changeWindowSize();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                GameLoop.stop();
            }
        });

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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

    public void changeWindowSize() {

        fullScreen = !fullScreen;

        frame.dispose();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        Dimension fullDim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (fullDim.width / GameLoop.SCALE);
        int height = (int) (fullDim.height / GameLoop.SCALE);
        if (fullScreen) {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            GameLoop.SCALE = 2f;
        } else {
            frame.setUndecorated(false);
            device.setFullScreenWindow(null);
            GameLoop.SCALE = 1f;
        }
        Dimension dim = new Dimension((int) (width * GameLoop.SCALE), (int) (height * GameLoop.SCALE));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        frame.setLayout(new BorderLayout());

        frame.add(canvas, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }

    public void close() {
        frame.dispose();
    }
}
