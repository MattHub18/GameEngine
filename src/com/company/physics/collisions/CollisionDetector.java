package com.company.physics.collisions;

import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.physics.primitives.Circle;

public class CollisionDetector {

    public static boolean isCollided(Collider... colliders) {

        String type = getTypeColliders(colliders);

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

    public static AxisAlignedBoundingBox intersection(Collider... colliders) {

        String type = getTypeColliders(colliders);

        switch (type) {
            case "cc":
                return null;
            case "aa":
                return AABBIntersectAABB((AxisAlignedBoundingBox) colliders[0], (AxisAlignedBoundingBox) colliders[1]);
            case "ca":
                return null;
            case "ac":
                return null;
            default:
                return null;
        }
    }

    private static String getTypeColliders(Collider... colliders) {
        StringBuilder typeOfCollision = new StringBuilder();

        for (Collider coll : colliders) {
            if (coll instanceof Circle)
                typeOfCollision.append('c');
            else if (coll instanceof AxisAlignedBoundingBox)
                typeOfCollision.append('a');
        }
        return typeOfCollision.toString();
    }

    private static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector vectorBetweenCenters = new Vector(c1.getCenter()).sub(c2.getCenter());
        float radiusSum = c1.getRadius() + c2.getRadius();
        return vectorBetweenCenters.lengthSquared() <= radiusSum * radiusSum;
    }

    private static boolean AABBAndAABB(AxisAlignedBoundingBox box1, AxisAlignedBoundingBox box2) {
        Vector distance = box2.getCenter().sub(box1.getCenter());
        Vector b1Size = (box1.getMax().sub(box1.getMin())).mul(0.5f);
        Vector b2Size = (box2.getMax().sub(box2.getMin())).mul(0.5f);
        return distance.lengthSquared() < b1Size.lengthSquared() + b2Size.lengthSquared();
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

    private static AxisAlignedBoundingBox AABBIntersectAABB(AxisAlignedBoundingBox body, AxisAlignedBoundingBox tile) {

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
}
