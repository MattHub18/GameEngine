package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.entities.human.movable.MovableInterface;

import java.io.Serializable;

public class LeftMoveCommand extends Command implements Serializable {

    public LeftMoveCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((MovableInterface) element).moveLeft();
    }
}
