package com.company.commands.system_command;

import com.company.commands.Command;
import com.company.graphic.primitives.GameLoop;

public class PauseCommand extends Command {

    public PauseCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((GameLoop) element).nextState("Pause");
    }
}
