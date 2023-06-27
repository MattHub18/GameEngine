package com.company.physics.collisions;

import com.company.physics.basics.RigidBody;
import com.company.physics.basics.Vector;

public class RoundBoundingBox implements Collider {
    private final RigidBody body;
    private final int radius;

    public RoundBoundingBox(Vector center, int radius) {
        this.body = new RigidBody(center);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public Vector getCenter() {
        return body.getPosition();
    }

    @Override
    public boolean collision(Collider collider) {
        if (collider instanceof RoundBoundingBox)
            return circleCollideCircle(this, (RoundBoundingBox) collider);
        else if (collider instanceof AxisAlignedBoundingBox)
            return circleCollideRectangle(this, (AxisAlignedBoundingBox) collider);

        return false;
    }

    @Override
    public AxisAlignedBoundingBox intersect(Collider collider) {
        if (collider instanceof RoundBoundingBox)
            return circleIntersectCircle(this, (RoundBoundingBox) collider);
        else if (collider instanceof AxisAlignedBoundingBox)
            return circleIntersectRectangle(this, (AxisAlignedBoundingBox) collider);

        return null;
    }

    private boolean circleCollideCircle(RoundBoundingBox c1, RoundBoundingBox c2) {
        Vector vectorBetweenCenters = new Vector(c1.getCenter()).sub(c2.getCenter());
        float radiusSum = c1.getRadius() + c2.getRadius();
        return vectorBetweenCenters.lengthSquared() <= radiusSum * radiusSum;
    }

    private boolean circleCollideRectangle(RoundBoundingBox circle, AxisAlignedBoundingBox box) {
        Vector min = box.getMin();
        Vector max = box.getMax();
        Vector closestPointToCircle = new Vector(circle.getCenter());

        if (closestPointToCircle.getX() < min.getX())
            closestPointToCircle.setX(min.getX());
        else if (closestPointToCircle.getX() > max.getX())
            closestPointToCircle.setX(max.getX());

        if (closestPointToCircle.getY() < min.getY())
            closestPointToCircle.setY(min.getY());
        else if (closestPointToCircle.getY() > max.getY())
            closestPointToCircle.setY(max.getY());

        Vector circleToBox = new Vector(circle.getCenter()).sub(closestPointToCircle);
        return circleToBox.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    private AxisAlignedBoundingBox circleIntersectCircle(RoundBoundingBox circle, RoundBoundingBox collider) {
        return null;
    }

    private AxisAlignedBoundingBox circleIntersectRectangle(RoundBoundingBox circle, AxisAlignedBoundingBox collider) {
        return null;
    }
}
