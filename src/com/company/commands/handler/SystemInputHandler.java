package com.company.commands.handler;

import com.company.commands.Command;
import com.company.commands.InputHandler;
import com.company.commands.system_command.WindowSizeCommand;

import java.awt.event.KeyEvent;

public class SystemInputHandler extends InputHandler {
    @Override
    public void insertCommands() {
        addCommand(new WindowSizeCommand(Command.KEYBOARD, KeyEvent.VK_F11, Command.DOWN));
    }
}
