package com.company.graphic.major;

import com.company.graphic.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private final BufferedImage image;
    private final Canvas canvas;
    private final BufferStrategy bs;
    private final Graphics g;

    public Window(GameLoop gl) {
        image = new BufferedImage(GameLoop.WIDTH, GameLoop.HEIGHT, BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();
        Dimension dim = new Dimension((int) (GameLoop.WIDTH * GameLoop.SCALE), (int) (GameLoop.HEIGHT * GameLoop.SCALE));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        JFrame frame = new JFrame(gl.getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

}
