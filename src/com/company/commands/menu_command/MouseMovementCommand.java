package com.company.commands.menu_command;

import com.company.menu.Menu;

public class MouseMovementCommand {
    public void execute(Object element, int mouseX, int mouseY) {
        ((Menu) element).setMouseCoordinates(mouseX, mouseY);
    }
}
