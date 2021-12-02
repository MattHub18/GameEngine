package com.company.commands.system_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.graphic.primitives.GameLoop;

public class WindowSizeCommand extends Command {

    public WindowSizeCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((GameLoop) element).changeWindowSize();
    }
}
