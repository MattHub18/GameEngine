package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Image;
import com.company.graphic.wrappers.ImageWrapper;

import java.util.ArrayList;

public class ImageRender implements RenderInterface {

    private final ArrayList<ImageWrapper> images;
    private final BasicRender basicRender;

    public ImageRender(BasicRender basicRender) {
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
        int[] structure = basicRender.cameraShift(offX, offY, image.getWidth(), image.getHeight(), image.isMovable());

        int startX = structure[0];
        int startY = structure[1];
        int width = structure[2];
        int height = structure[3];
        int camX = structure[4];
        int camY = structure[5];

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                basicRender.setPixel(x + offX - camX, y + offY - camY, image.getPixels()[x + y * image.getWidth()]);
                basicRender.setBrightness(x + offX - camX, y + offY - camY, image.isOpaque());
            }
        }
    }
}
