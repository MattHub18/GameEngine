package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.entities.human.shooting.ShootingInterface;

public class ShootingCommand extends Command {

    public ShootingCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public <T> void execute(T element) {
        ((ShootingInterface) element).shooting();
    }
}
