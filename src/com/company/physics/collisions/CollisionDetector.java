package com.company.physics.collisions;

import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.physics.primitives.Circle;

public class CollisionDetector {

    public static boolean isCollided(Collider... colliders) {
        StringBuilder typeOfCollision = new StringBuilder();

        for (Collider coll : colliders) {
            if (coll instanceof Circle)
                typeOfCollision.append('c');
            else if (coll instanceof AxisAlignedBoundingBox)
                typeOfCollision.append('a');
        }

        String type = typeOfCollision.toString();

        switch (type) {
            case "cc":
                return circleAndCircle((Circle) colliders[0], (Circle) colliders[1]);
            case "aa":
                return AABBAndAABB((AxisAlignedBoundingBox) colliders[0], (AxisAlignedBoundingBox) colliders[1]);
            case "ca":
                return circleAndAABB((Circle) colliders[0], (AxisAlignedBoundingBox) colliders[1]);
            case "ac":
                return circleAndAABB((Circle) colliders[1], (AxisAlignedBoundingBox) colliders[0]);
            default:
                return false;
        }
    }

    private static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector vectorBetweenCenters = new Vector(c1.getCenter()).sub(c2.getCenter());
        float radiusSum = c1.getRadius() + c2.getRadius();
        return vectorBetweenCenters.lengthSquared() <= radiusSum * radiusSum;
    }

    private static boolean AABBAndAABB(AxisAlignedBoundingBox box1, AxisAlignedBoundingBox box2) {
        Vector[] axesToTest = {new Vector(0, 1), new Vector(1, 0)};
        for (Vector vector : axesToTest) {
            if (!overlapOnAxis(box1, box2, vector))
                return false;
        }
        return true;
    }

    private static boolean overlapOnAxis(AxisAlignedBoundingBox box1, AxisAlignedBoundingBox box2, Vector axis) {
        Vector interval1 = getInterval(box1, axis);
        Vector interval2 = getInterval(box2, axis);

        return ((interval2.getX() <= interval1.getY()) && (interval1.getX() <= interval2.getY()));
    }

    private static Vector getInterval(AxisAlignedBoundingBox rect, Vector axis) {
        Vector result = new Vector(0, 0);

        Vector min = rect.getMin();
        Vector max = rect.getMax();

        Vector[] vertices = {
                new Vector(min.getX(), min.getY()), new Vector(min.getX(), max.getY()), new Vector(max.getX(), min.getY()), new Vector(max.getX(), max.getY())
        };

        result.setX(axis.dot(vertices[0]));
        result.setY(result.getX());

        for (int i = 1; i < 4; i++) {
            float projection = axis.dot(vertices[i]);
            if (projection < result.getX())
                result.setX(projection);
            if (projection > result.getY())
                result.setY(projection);
        }

        return result;
    }

    private static boolean circleAndAABB(Circle circle, AxisAlignedBoundingBox box) {
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
}
