package com.company.commands;

import com.company.graphic.primitives.Controller;
import com.company.graphic.primitives.GameLoop;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class InputHandler implements Serializable {

    private final ArrayList<Command> commands;

    protected Controller controller;

    public InputHandler() {
        commands = new ArrayList<>();
        controller = null;
    }

    public <T> void handleInput(GameLoop gl, T element) {
        if (controller == null)
            controller = gl.getController();
        for (Command command : commands) {
            CommandType type = command.getType();
            if (type == CommandType.KEYBOARD)
                handleInputKeys(command, element);
            else
                handleInputButtons(command, element);
        }
    }

    private <T> void handleInputKeys(Command command, T element) {
        switch (command.getMode()) {
            default: {
                if (controller.isKey(command.getKey()))
                    command.execute(element);
                break;
            }

            case UP: {
                if (controller.isKeyUp(command.getKey()))
                    command.execute(element);
                break;
            }

            case DOWN: {
                if (controller.isKeyDown(command.getKey()))
                    command.execute(element);
                break;
            }
        }
    }

    private <T> void handleInputButtons(Command command, T element) {
        switch (command.getMode()) {
            default: {
                if (controller.isButton(command.getKey()))
                    command.execute(element);
                break;
            }

            case UP: {
                if (controller.isButtonUp(command.getKey()))
                    command.execute(element);
                break;
            }

            case DOWN: {
                if (controller.isButtonDown(command.getKey()))
                    command.execute(element);
                break;
            }
        }
    }

    public void addCommand(Command c) {
        commands.add(c);
    }

    public abstract void insertCommands();
}