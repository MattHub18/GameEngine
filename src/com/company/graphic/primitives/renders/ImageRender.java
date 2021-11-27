package com.company.graphic.primitives.renders;

import com.company.graphic.gfx.Image;
import com.company.graphic.primitives.CameraShift;
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
        CameraShift structure = basicRender.cameraShift(offX, offY, image.getWidth(), image.getHeight(), image.isMovable());

        int startX = structure.getStartX();
        int startY = structure.getStartY();
        int width = structure.getWidth();
        int height = structure.getHeight();
        int camX = structure.getCamX();
        int camY = structure.getCamY();

        for (int y = startY; y < height; y++) {
            for (int x = startX; x < width; x++) {
                basicRender.setPixel(x + offX - camX, y + offY - camY, image.getPixels()[x + y * image.getWidth()]);
                basicRender.setBrightness(x + offX - camX, y + offY - camY, image.isOpaque());
            }
        }
    }
}
