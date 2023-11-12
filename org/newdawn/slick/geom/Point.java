/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Point
extends Shape {
    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.checkPoints();
    }

    @Override
    public Shape transform(Transform transform) {
        float[] fArray = new float[this.points.length];
        transform.transform(this.points, 0, fArray, 0, this.points.length / 2);
        return new Point(this.points[0], this.points[1]);
    }

    @Override
    protected void createPoints() {
        this.points = new float[2];
        this.points[0] = this.getX();
        this.points[1] = this.getY();
        this.maxX = this.x;
        this.maxY = this.y;
        this.minX = this.x;
        this.minY = this.y;
        this.findCenter();
        this.calculateRadius();
    }

    @Override
    protected void findCenter() {
        this.center = new float[2];
        this.center[0] = this.points[0];
        this.center[1] = this.points[1];
    }

    @Override
    protected void calculateRadius() {
        this.boundingCircleRadius = 0.0f;
    }
}

