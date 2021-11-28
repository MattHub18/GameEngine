package com.company.commands.system_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.graphic.primitives.GameLoop;

public class PauseCommand extends Command {

    private boolean pause = false;

    public PauseCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public <T> void execute(T element) {
        pause = !pause;
        String exec;
        if (!pause)
            exec = "GAME";
        else
            exec = "PAUSE";
        ((GameLoop) element).nextState(exec);
    }
}
