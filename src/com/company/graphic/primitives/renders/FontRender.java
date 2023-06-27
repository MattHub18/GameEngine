package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Font;

import java.util.ArrayList;

public class FontRender implements RenderInterface {

    private final ArrayList<Font> fonts;
    private final BasicRender basicRender;

    public FontRender(BasicRender basicRender) {
        this.basicRender = basicRender;
        this.fonts = new ArrayList<>();
    }

    @Override
    public void process() {
        for (Font font : fonts) {
            drawFont(font, font.getText(), font.getOffX(), font.getOffY(), font.getColor());
        }
    }

    @Override
    public void clear() {
        fonts.clear();
    }

    public void addFonts(Font font) {
        fonts.add(font);
    }

    private void drawFont(Font font, String text, int offX, int offY, int color) {

        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);

            int[] structure = basicRender.cameraShift(offX + offset, offY, font.getWidths()[unicode], font.getFontImage().getHeight(), font.isMovable());

            int startX = structure[0];
            int startY = structure[1];
            int width = structure[2];
            int height = structure[3];
            int camX = structure[4];
            int camY = structure[5];

            for (int y = startY; y < height; y++) {
                for (int x = startX; x < width; x++) {
                    if (font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
                        basicRender.setPixel(x + offset + offX - camX, y + offY - camY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];

        }
    }
}
