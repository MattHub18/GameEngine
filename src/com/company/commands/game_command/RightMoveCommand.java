package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.entities.human.movable.MovableInterface;

import java.io.Serializable;

public class RightMoveCommand extends Command implements Serializable {

    public RightMoveCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((MovableInterface) element).moveRight();
    }
}
