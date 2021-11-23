package com.company.graphic.primitives;

import java.awt.event.*;
import java.io.Serializable;

public class Controller implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Serializable {

    private final int NUM_KEYS = 256;
    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_MOUSE_BUTTONS = 5;
    private final boolean[] buttons = new boolean[NUM_MOUSE_BUTTONS];
    private final boolean[] buttonsLast = new boolean[NUM_MOUSE_BUTTONS];

    private int mouseX;
    private int mouseY;
    private int scroll;

    public Controller(Window window) {
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        window.getCanvas().addKeyListener(this);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getCanvas().addMouseWheelListener(this);
    }

    public void update() {

        scroll = 0;

        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);

        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_MOUSE_BUTTONS);
    }

    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    public boolean isButton(int buttonCode) {
        return buttons[buttonCode];
    }

    public boolean isButtonUp(int buttonCode) {
        return !buttons[buttonCode] && buttonsLast[buttonCode];
    }

    public boolean isButtonDown(int buttonCode) {
        return buttons[buttonCode] && !buttonsLast[buttonCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX() / GameLoop.SCALE);
        mouseY = (int) (e.getY() / GameLoop.SCALE);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / GameLoop.SCALE);
        mouseY = (int) (e.getY() / GameLoop.SCALE);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

}
