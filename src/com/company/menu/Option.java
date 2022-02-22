package com.company.menu;

import com.company.audio.AudioComponent;
import com.company.audio.Sound;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.ColorPalette;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.physics.basics.Vector;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

public abstract class Option implements Graphic {
    private final String name;
    private final int offX;
    private final int offY;
    private final int width;
    private final int height;
    private final int textColor;
    private final boolean enable;
    private final Rectangle darkHover;
    private final Rectangle lightHover;
    protected GameLoop gl;
    private boolean hover = false;
    private final AudioComponent over;
    private final Sound click;

    public Option(String name, int offX, int offY, int width, int height, int textColor, int backColor, boolean enable) {
        this.name = name;
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
        this.darkHover = new Rectangle(new Vector(offX, offY), new Vector(offX + width, offY + height), backColor, true, false);
        this.lightHover = new Rectangle(new Vector(offX, offY), new Vector(offX + width, offY + height), ColorPalette.INVISIBLE, true, false);
        this.enable = enable;
        over = new AudioComponent(SystemResources.OPTION_OVER);
        click = new Sound(Archive.SOUND.get(SystemResources.OPTION_CLICK));
    }

    public void onClick() {
        click.play();
    }


    public void update(GameLoop gl, float dt, int mouseX, int mouseY) {
        update(gl, dt);
        hover = mouseEntered(mouseX, mouseY) && enable;
        if (hover)
            over.update();
        else
            over.reset();
    }

    private boolean mouseEntered(int mouseX, int mouseY) {
        return mouseX >= offX && mouseX <= offX + width && mouseY >= offY && mouseY <= offY + height;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (this.gl == null)
            this.gl = gl;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (hover)
            r.addRectangle(darkHover);
        else
            r.addRectangle(lightHover);
        r.addFont(new Font(Archive.FONT.get(SystemResources.MENU_FONT), name, offX, offY, textColor, false));
    }

    public boolean isHover() {
        return hover;
    }
}
