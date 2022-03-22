package com.company.dialog;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.gfx.Image;
import com.company.graphic.gfx.TileImage;
import com.company.graphic.primitives.ColorPalette;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.SystemResources;
import com.company.resources.file_system.Archive;

public class Board implements Graphic {
    private final Image board;
    private final TileImage icon;
    private final Font next;
    private final int offX;
    private final int offY;
    private final int shiftX;
    private final int shiftY;
    private final DialogFormatter dialog;
    private boolean end;

    public Board(int offX, int offY, byte boardTexture, byte iconTexture, int tileWidth, int tileHeight, byte dialogPath, byte dialogFont, int color) {
        this.offX = offX;
        this.offY = offY;
        shiftX = 70;
        shiftY = 32;
        board = new Image(Archive.TEXTURES.get(boardTexture), false, true);
        icon = new TileImage(Archive.TEXTURES.get(iconTexture), tileWidth, tileHeight, false, true);
        next = new Font(Archive.FONT.get(SystemResources.FPS_FONT), "Next", offX + board.getWidth() - shiftX, offY + board.getHeight() - shiftY, ColorPalette.BLACK, false);
        dialog = new DialogFormatter(offX + 20, offY + 20, board.getHeight() - 40, dialogPath, Archive.FONT.get(dialogFont), color);
        end = false;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        dialog.update(gl, dt);
        if (end)
            gl.nextState("Game");
    }

    @Override
    public void render(GameLoop gl, Render r) {
        r.addImage(board, offX, offY);
        dialog.render(gl, r);
        r.addImage(icon.getTile(0, 0), offX + board.getWidth() - shiftX + 30, offY + board.getHeight() - shiftY + 3);
        r.addFont(next);
    }

    public void next() {
        end = !dialog.nextPage();
    }
}
