package com.company.menu;

import com.company.commands.InputHandler;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Image;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.resources.file_system.Archive;

import java.util.ArrayList;

public abstract class Menu implements Graphic, RenderObject {

    private final ArrayList<Option> options;
    private final Image background;
    private final InputHandler menuInputHandler;
    private Option currentOption;
    private int mouseX;
    private int mouseY;

    public Menu(byte textureBackgroundFilename, InputHandler menuInputHandler) {
        this.menuInputHandler = menuInputHandler;
        this.menuInputHandler.insertCommands();
        options = new ArrayList<>();
        insertOptions();
        background = new Image(Archive.TEXTURES.get(textureBackgroundFilename), false, true);
        currentOption = null;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        menuInputHandler.handleInput(this);
        for (Option option : options) {
            option.update(gl, dt, mouseX, mouseY);
        }

        for (Option option : options) {
            if (option.isHover()) {
                currentOption = option;
                return;
            }
        }
        currentOption = null;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addImage(background, 0, 0);
        for (Option option : options)
            option.render(gl, r);
    }

    @Override
    public int getWidthInPixel() {
        return background.getWidth();
    }

    @Override
    public int getHeightInPixel() {
        return background.getHeight();
    }

    protected void addOption(Option option) {
        options.add(option);
    }

    protected abstract void insertOptions();

    public void onClick() {
        if (currentOption != null)
            currentOption.onClick();
    }

    public void setMouseCoordinates(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
}
