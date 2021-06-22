package com.company.graphic.primitives;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private final BufferedImage image;
    private final Canvas canvas;
    private static Dimension fullDim;
    private final JFrame frame;
    private BufferStrategy bs;
    private Graphics g;
    private WindowHandler handler;

    public Window(GameLoop gl, WindowHandler handler) {
        image = new BufferedImage(gl.getCamera().getMapWidthInPixel(), gl.getCamera().getMapHeightInPixel(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();

        fullDim = Toolkit.getDefaultToolkit().getScreenSize();
        canvas.setPreferredSize(fullDim);
        canvas.setMaximumSize(fullDim);
        canvas.setMinimumSize(fullDim);

        frame = new JFrame(gl.getTitle());
        handler.setFrame(frame);
        handler.setGl(gl);

        changeWindowSize(gl);
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

    public void changeWindowSize(GameLoop gl) {
        frame.dispose();

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        if (gl.isFullScreen()) {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            fullDim = Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            frame.setUndecorated(false);
            device.setFullScreenWindow(null);
            fullDim = new Dimension((int) (GameLoop.WIDTH * GameLoop.SCALE), (int) (GameLoop.HEIGHT * GameLoop.SCALE));
        }
        canvas.setPreferredSize(fullDim);
        canvas.setMaximumSize(fullDim);
        canvas.setMinimumSize(fullDim);

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
