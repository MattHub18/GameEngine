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
        Vector distance = box2.getPosition().sub(box1.getPosition());
        Vector b1Size = box1.getMax().sub(box1.getMin());
        Vector b2Size = box2.getMax().sub(box2.getMin());
        return distance.lengthSquared() <= b1Size.lengthSquared() + b2Size.lengthSquared();
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
