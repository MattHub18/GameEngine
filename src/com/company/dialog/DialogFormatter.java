package com.company.dialog;

import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.resources.file_system.Archive;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DialogFormatter implements Graphic {
    private final int offX;
    private final int offY;
    private final int height;
    private final String fontPath;
    private final int fontColor;
    private final int lineHeight;
    private String[] lines;
    private int lineNumber;
    private int pageNumber;
    private boolean next;

    public DialogFormatter(int offX, int offY, int height, byte dialogPath, String fontPath, int fontColor) {
        this.offX = offX;
        this.offY = offY;
        this.height = height;
        this.fontPath = fontPath;
        this.fontColor = fontColor;
        lineHeight = new Font(fontPath, "", offX, offY, fontColor, false).getFontImage().getHeight();
        try {
            lines = Files.readAllLines(Paths.get(Archive.DIALOG.get(dialogPath)), StandardCharsets.US_ASCII).toArray(new String[0]);
            lineNumber = lines.length;
            pageNumber = 0;
            next = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameLoop gl, float dt) {
        if (lineNumber * lineHeight > height) {
            lineNumber = height / lineHeight;
            next = true;
        }

        if (lines.length - (pageNumber + 1) * lineNumber > 0)
            next = true;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        int ln = lineNumber;
        int dif = lines.length - pageNumber * lineNumber;
        if (dif < lineNumber)
            ln = dif;
        for (int i = 0; i < ln; i++) {
            Font font = new Font(fontPath, lines[i + pageNumber * lineNumber], offX, offY + i * lineHeight, fontColor, false);
            r.addFont(font);
        }
    }

    public boolean nextPage() {
        if (next) {
            next = false;
            pageNumber++;
            return true;
        }
        return false;
    }
}
