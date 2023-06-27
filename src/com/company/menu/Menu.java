package com.company.menu;

import com.company.audio.Sound;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Image;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.RenderObject;
import com.company.input.Controller;
import com.company.input.InputHandler;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

import java.util.ArrayList;

public abstract class Menu implements Graphic, RenderObject {

    private final ArrayList<Option> options;
    private final Image background;
    private final InputHandler inputHandler;
    private Option currentOption;
    private int mouseX;
    private int mouseY;
    protected Sound theme;

    public Menu(byte textureBackgroundFilename, byte themeFilename, InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        options = new ArrayList<>();
        insertOptions();
        background = new Image(Archive.TEXTURES.get(textureBackgroundFilename), false, true);
        currentOption = null;
        theme = null;
        if (themeFilename != SystemResources.NO_SOUND) {
            theme = new Sound(Archive.SOUND.get(themeFilename));
            theme.loop();
        }
    }

    @Override
    public void update(Engine engine, float dt) {
        inputHandler.handleInput(this);
        for (Option option : options) {
            option.update(engine, dt, mouseX, mouseY);
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
    public void render(Render r) {
        r.addImage(background, 0, 0);
        for (Option option : options)
            option.render(r);
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

    public void stopSound() {
        if (theme != null)
            theme.close();
    }

    public void connectController(Controller controller) {
        inputHandler.registerController(controller);
    }
}
