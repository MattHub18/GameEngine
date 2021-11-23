package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Image;
import com.company.graphic.primitives.Camera;
import com.company.graphic.wrappers.ImageWrapper;

import java.util.ArrayList;

public class ImageRender implements RenderInterface {

    private final ArrayList<ImageWrapper> images;
    private final Camera camera;
    private final BasicRender basicRender;

    public ImageRender(Camera camera, BasicRender basicRender) {
        this.camera = camera;
        this.basicRender = basicRender;
        this.images = new ArrayList<>();
    }

    @Override
    public void process() {
        for (ImageWrapper img : images) {
            drawImage(img.getImage(), img.getOffX(), img.getOffY());
        }
    }

    @Override
    public void clear() {
        images.clear();
    }

    public void addImage(ImageWrapper image) {
        images.add(image);
    }

    private void drawImage(Image image, int offX, int offY) {
        camera.centerCamera();

        int camX = camera.getCamX();
        int camY = camera.getCamY();
        int maxViewX = camera.getMaxViewX();
        int maxViewY = camera.getMaxViewY();

        int startX = 0;
        int startY = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        if (image.isMovable()) {
            camX = 0;
            camY = 0;
            maxViewX = camera.getMapWidthInPixel();
            maxViewY = camera.getMapHeightInPixel();
        }

        if (offX < camX)
            startX -= (offX - camX);
        if (offY < camY)
            startY -= (offY - camY);
        if (offX + width >= maxViewX)
            width -= (width + offX - maxViewX);
        if (offY + height >= maxViewY)
            height -= (height + offY - maxViewY);

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                basicRender.setPixel(x + offX - camX, y + offY - camY, image.getPixels()[x + y * image.getWidth()]);
                basicRender.setBrightness(x + offX, y + offY, image.isOpaque());
            }
        }
    }
}
