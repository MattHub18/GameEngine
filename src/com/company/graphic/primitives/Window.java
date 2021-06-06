package com.company.graphic.primitives;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private final BufferedImage image;
    private final Canvas canvas;
    private final BufferStrategy bs;
    private final Graphics g;

    public Window(GameLoop gl, WindowHandler handler) {
        image = new BufferedImage(gl.getCamera().getMapWidthInPixel(), gl.getCamera().getMapHeightInPixel(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();
        Dimension dim = new Dimension((int) (GameLoop.WIDTH * GameLoop.SCALE), (int) (GameLoop.HEIGHT * GameLoop.SCALE));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        JFrame frame = new JFrame(gl.getTitle());
        handler.setFrame(frame);
        handler.setGl(gl);
        frame.addWindowListener(handler);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
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

}
