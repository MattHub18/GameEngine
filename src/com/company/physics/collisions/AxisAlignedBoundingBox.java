package com.company.physics.collisions;

import com.company.physics.basics.RigidBody;
import com.company.physics.basics.Vector;

public class AxisAlignedBoundingBox implements Collider {
    private final Vector halfSize;
    private final RigidBody body;

    public AxisAlignedBoundingBox(Vector min, Vector max) {
        Vector size = new Vector(max).sub(min);
        this.halfSize = new Vector(size).mul(0.5f);
        this.body = new RigidBody(new Vector(halfSize).add(min));
    }

    public Vector getMin() {
        return new Vector(this.body.getPosition()).sub(this.halfSize);
    }

    public Vector getMax() {
        return new Vector(this.body.getPosition()).add(this.halfSize);
    }

    @Override
    public Vector getCenter() {
        return new Vector(this.body.getPosition());
    }

    @Override
    public boolean collision(Collider collider) {
        if (collider instanceof RoundBoundingBox)
            return collider.collision(this);
        else if (collider instanceof AxisAlignedBoundingBox)
            return rectangleCollideRectangle(this, (AxisAlignedBoundingBox) collider);

        return false;
    }

    @Override
    public AxisAlignedBoundingBox intersect(Collider collider) {
        if (collider instanceof RoundBoundingBox)
            return collider.intersect(this);
        else if (collider instanceof AxisAlignedBoundingBox)
            return rectangleIntersectRectangle(this, (AxisAlignedBoundingBox) collider);

        return null;
    }


    public float getWidth() {
        return getMax().sub(getMin()).getX();
    }

    public float getHeight() {
        return getMax().sub(getMin()).getY();
    }

    private boolean rectangleCollideRectangle(AxisAlignedBoundingBox box1, AxisAlignedBoundingBox box2) {
        Vector distance = box2.getCenter().sub(box1.getCenter());
        Vector b1Size = (box1.getMax().sub(box1.getMin())).mul(0.5f);
        Vector b2Size = (box2.getMax().sub(box2.getMin())).mul(0.5f);
        return distance.lengthSquared() < b1Size.lengthSquared() + b2Size.lengthSquared();
    }

    private AxisAlignedBoundingBox rectangleIntersectRectangle(AxisAlignedBoundingBox body, AxisAlignedBoundingBox tile) {

        float minX = body.getMin().getX();
        float minY = body.getMin().getY();
        float maxX = body.getMax().getX();
        float maxY = body.getMax().getY();

        if (body.getMin().getX() < tile.getMin().getX())
            minX = tile.getMin().getX();

        if (body.getMin().getY() < tile.getMin().getY())
            minY = tile.getMin().getY();

        if (body.getMax().getX() > tile.getMax().getX())
            maxX = tile.getMax().getX();

        if (body.getMax().getY() > tile.getMax().getY())
            maxY = tile.getMax().getY();

        return new AxisAlignedBoundingBox(new Vector(minX, minY), new Vector(maxX, maxY));
    }

    public float getStartX() {
        return getMin().getX();
    }

    public float getStartY() {
        return getMin().getY();
    }
}
