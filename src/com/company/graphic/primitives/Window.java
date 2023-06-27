package com.company.graphic.primitives;

import com.company.graphic.Engine;
import com.company.resources.SystemConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private final int width;
    private final int height;

    private final BufferedImage image;
    private final Canvas canvas;
    private final JFrame frame;
    private BufferStrategy bs;
    private Graphics g;

    private boolean fullScreen = true;

    private Engine engine;

    public Window(String title) {

        Dimension fullDim = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) (fullDim.width / SystemConstants.SCALE);
        height = (int) (fullDim.height / SystemConstants.SCALE);

        canvas = new Canvas();

        frame = new JFrame(title);

        changeWindowSize();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                engine.stop();
            }
        });

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void update() {
        g.drawImage(image, 0, 0, (int) (image.getWidth() * SystemConstants.SCALE), (int) (image.getHeight() * SystemConstants.SCALE), null);
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
        int width = (int) (fullDim.width / SystemConstants.SCALE);
        int height = (int) (fullDim.height / SystemConstants.SCALE);
        if (fullScreen) {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
            SystemConstants.SCALE *= 2;
        } else {
            frame.setUndecorated(false);
            device.setFullScreenWindow(null);
            SystemConstants.SCALE /= 2;
        }
        Dimension dim = new Dimension((int) (width * SystemConstants.SCALE), (int) (height * SystemConstants.SCALE));
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

        canvas.requestFocus();
    }

    public void close() {
        frame.dispose();
    }

    public void registerEngine(Engine engine) {
        this.engine = engine;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
