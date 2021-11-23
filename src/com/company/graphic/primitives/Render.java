package com.company.graphic.primitives;

import com.company.graphic.gfx.*;
import com.company.graphic.primitives.renders.*;
import com.company.graphic.wrappers.ImageWrapper;

public class Render implements RenderInterface {

    private final BasicRender basicRender;

    private final LightRender lightRender;
    private final RectangleRender rectangleRender;
    private final FontRender fontRender;
    private final ImageRender imageRender;
    private final CircleRender circleRender;


    public Render(Camera camera, Window window) {

        this.basicRender = new BasicRender(camera, window);
        this.lightRender = new LightRender(basicRender);
        this.rectangleRender = new RectangleRender(basicRender);
        this.fontRender = new FontRender(basicRender);
        this.imageRender = new ImageRender(camera, basicRender);
        this.circleRender = new CircleRender(basicRender);
    }

    @Override
    public void process() {

        imageRender.process();
        rectangleRender.process();
        circleRender.process();
        fontRender.process();
        lightRender.process();
        basicRender.process();

        clearProcess();
    }

    private void clearProcess() {
        imageRender.clear();
        rectangleRender.clear();
        fontRender.clear();
        lightRender.clear();
        circleRender.clear();
    }

    @Override
    public void clear() {
        basicRender.clear();
    }

    public void addRectangle(Rectangle rect) {
        rectangleRender.addRectangle(rect);
    }

    public void addThickRectangle(int smallestOffX, int smallestOffY, int biggestWidth, int biggestHeight, int color, int thickness) {
        rectangleRender.addThickRectangle(smallestOffX, smallestOffY, biggestWidth, biggestHeight, color, thickness);
    }

    public void addCircle(Circle circle) {
        circleRender.addCircle(circle);
    }

    public void addImage(Image image, int offX, int offY) {
        imageRender.addImage(new ImageWrapper(image, offX, offY));
    }

    public void addFont(Font font) {
        fontRender.addFonts(font);
    }

    public void addLight(Light light) {
        lightRender.addLight(light);
    }
}
