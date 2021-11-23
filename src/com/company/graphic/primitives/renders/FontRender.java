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
            for (int y = 0; y < font.getFontImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
                        basicRender.setPixel(x + offset + offX, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }
}
