package com.company.graphic.gfx;

import com.company.physics.basics.RigidBody;
import com.company.physics.basics.Vector;
import com.company.physics.collisions.Collider;

public class Circle implements Collider {
    private final int radius;
    private final int color;
    private final boolean isFull;
    private final RigidBody body;
    private final boolean movable;

    public Circle(Vector center, int radius, int color, boolean isFull, boolean movable) {
        this.body = new RigidBody(center);
        this.radius = radius;
        this.color = color;
        this.isFull = isFull;
        this.movable = movable;
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
        if (collider instanceof Circle)
            return circleCollideCircle(this, (Circle) collider);
        else if (collider instanceof Rectangle)
            return circleCollideRectangle(this, (Rectangle) collider);

        return false;
    }

    @Override
    public Rectangle intersect(Collider collider) {
        if (collider instanceof Circle)
            return circleIntersectCircle(this, (Circle) collider);
        else if (collider instanceof Rectangle)
            return circleIntersectRectangle(this, (Rectangle) collider);

        return null;
    }

    private boolean circleCollideCircle(Circle c1, Circle c2) {
        Vector vectorBetweenCenters = new Vector(c1.getCenter()).sub(c2.getCenter());
        float radiusSum = c1.getRadius() + c2.getRadius();
        return vectorBetweenCenters.lengthSquared() <= radiusSum * radiusSum;
    }

    private boolean circleCollideRectangle(Circle circle, Rectangle box) {
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

    private Rectangle circleIntersectCircle(Circle circle, Circle collider) {
        return null;
    }

    private Rectangle circleIntersectRectangle(Circle circle, Rectangle collider) {
        return null;
    }

    public int getColor() {
        return color;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isMovable() {
        return movable;
    }
}
