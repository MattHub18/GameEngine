package com.company.commands.game_command;

import com.company.commands.Command;
import com.company.entities.human.shooting.ShootingInterface;

public class ShootingCommand extends Command {

    public ShootingCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((ShootingInterface) element).shooting();
    }
}
