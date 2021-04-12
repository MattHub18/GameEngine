package com.company.physics.collisions;

import com.company.physics.basics.Vector;
import com.company.physics.primitives.AxisAlignedBoundingBox;
import com.company.physics.primitives.Circle;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollisionDetectorTest {

    private AxisAlignedBoundingBox box = new AxisAlignedBoundingBox(new Vector(0, 0), new Vector(5, 5));
    private Circle circle = new Circle(new Vector(7, 3), 3);


    @Test
    public void aabbAabbCollision() {
        assertTrue(CollisionDetector.isCollided(box, box));
    }

    @Test
    public void circleCircleCollision() {
        assertTrue(CollisionDetector.isCollided(circle, circle));
    }

    @Test
    public void circleAabbCollision() {
        assertTrue(CollisionDetector.isCollided(circle, box));
    }

    private AxisAlignedBoundingBox box1 = new AxisAlignedBoundingBox(new Vector(9, 9), new Vector(10, 10));
    private Circle circle1 = new Circle(new Vector(20, 20), 1);

    @Test
    public void aabbAabbDistinct() {
        assertFalse(CollisionDetector.isCollided(box, box1));
    }

    @Test
    public void circleCircleDistinct() {
        assertFalse(CollisionDetector.isCollided(circle, circle1));
    }

    @Test
    public void circleAabbDistinct() {
        assertFalse(CollisionDetector.isCollided(circle1, box1));
    }

    private AxisAlignedBoundingBox box2 = new AxisAlignedBoundingBox(new Vector(5, 0), new Vector(10, 5));
    private Circle circle2 = new Circle(new Vector(13, 3), 3);

    @Test
    public void aabbAabbTan() {
        assertTrue(CollisionDetector.isCollided(box, box2));
    }

    @Test
    public void circleCircleTan() {
        assertTrue(CollisionDetector.isCollided(circle, circle2));
    }

    private Circle circle3 = new Circle(new Vector(8, 2.5f), 3);

    @Test
    public void circleAabbTan() {
        assertTrue(CollisionDetector.isCollided(circle3, box));
    }

}