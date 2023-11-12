/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.NonGeometricData;

public class Figure {
    public static final int ELLIPSE = 1;
    public static final int LINE = 2;
    public static final int RECTANGLE = 3;
    public static final int PATH = 4;
    public static final int POLYGON = 5;
    private int type;
    private Shape shape;
    private NonGeometricData data;
    private Transform transform;

    public Figure(int n, Shape shape, NonGeometricData nonGeometricData, Transform transform) {
        this.shape = shape;
        this.data = nonGeometricData;
        this.type = n;
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public int getType() {
        return this.type;
    }

    public Shape getShape() {
        return this.shape;
    }

    public NonGeometricData getData() {
        return this.data;
    }
}

