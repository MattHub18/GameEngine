package com.company.commands.menu_command;

import com.company.commands.Command;
import com.company.commands.CommandType;
import com.company.commands.InputMode;
import com.company.menu.Menu;

public class SelectOptionCommand extends Command {
    public SelectOptionCommand(CommandType type, int key, InputMode mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((Menu) element).onClick();
    }
}
