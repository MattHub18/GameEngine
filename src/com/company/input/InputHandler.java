package com.company.input;

import java.util.ArrayList;

public abstract class InputHandler {

    private final ArrayList<Command> commands;

    protected Controller controller;

    public InputHandler() {
        this.commands = new ArrayList<>();
        this.insertCommands();
    }

    public void handleInput(Object element) {
        for (Command command : commands) {
            if (command.getType() == Command.KEYBOARD)
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

    protected abstract void insertCommands();

    public void registerController(Controller controller) {
        this.controller = controller;
    }
}
