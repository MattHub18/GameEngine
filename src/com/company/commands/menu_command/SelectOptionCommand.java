package com.company.commands.menu_command;

import com.company.commands.Command;
import com.company.menu.Menu;

public class SelectOptionCommand extends Command {
    public SelectOptionCommand(byte type, int key, byte mode) {
        super(type, key, mode);
    }

    @Override
    public void execute(Object element) {
        ((Menu) element).onClick();
    }
}
