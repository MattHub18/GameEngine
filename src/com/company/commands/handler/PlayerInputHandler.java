package com.company.commands.handler;

import com.company.commands.Command;
import com.company.commands.InputHandler;
import com.company.commands.game_command.DownMoveCommand;
import com.company.commands.game_command.LeftMoveCommand;
import com.company.commands.game_command.RightMoveCommand;
import com.company.commands.game_command.UpMoveCommand;

import java.awt.event.KeyEvent;

public class PlayerInputHandler extends InputHandler {
    @Override
    public void insertCommands() {
        addCommand(new UpMoveCommand(Command.KEYBOARD, KeyEvent.VK_W, Command.BASIC));
        addCommand(new LeftMoveCommand(Command.KEYBOARD, KeyEvent.VK_A, Command.BASIC));
        addCommand(new DownMoveCommand(Command.KEYBOARD, KeyEvent.VK_S, Command.BASIC));
        addCommand(new RightMoveCommand(Command.KEYBOARD, KeyEvent.VK_D, Command.BASIC));
    }
}
