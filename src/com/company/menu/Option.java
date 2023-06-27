package com.company.menu;

import com.company.audio.Sound;
import com.company.graphic.Engine;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Rectangle;
import com.company.graphic.primitives.Render;
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
    private final Sound over;
    private boolean hover = false;
    protected Engine engine;
    private final Sound click;

    public Option(String name, int offX, int offY, int width, int height, int textColor, int backColor, boolean enable) {
        this.name = name;
        this.offX = offX;
        this.offY = offY;
        this.width = width;
        this.height = height;
        this.textColor = textColor;
        this.darkHover = new Rectangle(offX, offY, width, height, true, backColor, false);
        this.enable = enable;
        this.over = new Sound(Archive.SOUND.get(SystemResources.OPTION_OVER));
        this.click = new Sound(Archive.SOUND.get(SystemResources.OPTION_CLICK));
    }

    public void onClick() {
        click.play();
    }

    public void update(Engine engine, float dt, int mouseX, int mouseY) {
        hover = mouseEntered(mouseX, mouseY) && enable;
        if (this.engine == null)
            this.engine = engine;
        update(engine, dt);
    }

    private boolean mouseEntered(int mouseX, int mouseY) {
        return mouseX >= offX && mouseX <= offX + width && mouseY >= offY && mouseY <= offY + height;
    }

    @Override
    public void update(Engine engine, float dt) {
        if (hover)
            over.play();
    }

    @Override
    public void render(Render r) {
        if (hover)
            r.addRectangle(darkHover);
        r.addFont(new Font(Archive.FONT.get(SystemResources.MENU_FONT), name, offX, offY, textColor, false));
    }

    public boolean isHover() {
        return hover;
    }
}
