package com.company.commands.system_command;

import com.company.commands.Command;
import com.company.graphic.primitives.GameLoop;

public class WindowSizeCommand extends Command {

    public WindowSizeCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((GameLoop) element).changeWindowSize();
    }
}
