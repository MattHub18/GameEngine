package com.company.commands.handler;

import com.company.commands.Command;
import com.company.commands.InputHandler;
import com.company.commands.system_command.PauseCommand;

import java.awt.event.KeyEvent;

public class GameInputHandler extends InputHandler {
    @Override
    public void insertCommands() {
        addCommand(new PauseCommand(Command.KEYBOARD, KeyEvent.VK_P, Command.DOWN));
    }
}
