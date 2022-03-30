package com.company.commands;

import com.company.graphic.primitives.Controller;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class InputHandler implements Serializable {

    private final ArrayList<Command> commands;

    protected Controller controller;

    public InputHandler() {
        commands = new ArrayList<>();
    }

    public void handleInput(Object element) {
        controller = Controller.getInstance();
        for (Command command : commands) {
            byte type = command.getType();
            if (type == Command.KEYBOARD)
                handleInputKeys(command, element);
            else
                handleInputButtons(command, element);
        }
    }

    private void handleInputKeys(Command command, Object element) {
        switch (command.getMode()) {
            default: {
                if (controller.isKey(command.getKey()))
                    command.execute(element);
                break;
            }

            case Command.UP: {
                if (controller.isKeyUp(command.getKey()))
                    command.execute(element);
                break;
            }

            case Command.DOWN: {
                if (controller.isKeyDown(command.getKey()))
                    command.execute(element);
                break;
            }
        }
    }

    private void handleInputButtons(Command command, Object element) {
        switch (command.getMode()) {
            default: {
                if (controller.isButton(command.getKey()))
                    command.execute(element);
                break;
            }

            case Command.UP: {
                if (controller.isButtonUp(command.getKey()))
                    command.execute(element);
                break;
            }

            case Command.DOWN: {
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
