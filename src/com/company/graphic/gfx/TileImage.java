package com.company.graphic.gfx;

public class TileImage extends Image {

    private final int tileWidth;
    private final int tileHeight;

    public TileImage(String path, int tileW, int tileH, boolean movable, boolean opaque) {
        super(path, movable, opaque);
        this.tileWidth = tileW;
        this.tileHeight = tileH;
    }

    @Override
    public int getWidth() {
        return tileWidth;
    }

    @Override
    public int getHeight() {
        return tileHeight;
    }

    public Image getTile(int row, int column) {
        int[] pixels = new int[tileWidth * tileHeight];
        for (int y = 0; y < tileHeight; y++) {
            for (int x = 0; x < tileWidth; x++) {
                pixels[x + y * tileWidth] = this.getPixels()[(x + column * tileWidth) + (y + row * tileHeight) * super.getWidth()];
            }
        }
        return new Image(pixels, tileWidth, tileHeight, isMovable(), isOpaque());
    }
}
