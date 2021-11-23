package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.entities.human.movable.MovableInterface;

import java.io.Serializable;

public class DownMoveCommand extends Command implements Serializable {

    public DownMoveCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public <T> void execute(T element) {
        ((MovableInterface) element).moveDown();
    }
}
