/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.util.FastTrig;

public class Ellipse
extends Shape {
    protected static final int DEFAULT_SEGMENT_COUNT = 50;
    private int segmentCount;
    private float radius1;
    private float radius2;

    public Ellipse(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, 50);
    }

    public Ellipse(float f, float f2, float f3, float f4, int n) {
        this.x = f - f3;
        this.y = f2 - f4;
        this.radius1 = f3;
        this.radius2 = f4;
        this.segmentCount = n;
        this.checkPoints();
    }

    public void setRadii(float f, float f2) {
        this.setRadius1(f);
        this.setRadius2(f2);
    }

    public float getRadius1() {
        return this.radius1;
    }

    public void setRadius1(float f) {
        if (f != this.radius1) {
            this.radius1 = f;
            this.pointsDirty = true;
        }
    }

    public float getRadius2() {
        return this.radius2;
    }

    public void setRadius2(float f) {
        if (f != this.radius2) {
            this.radius2 = f;
            this.pointsDirty = true;
        }
    }

    @Override
    protected void createPoints() {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
        float f = 0.0f;
        float f2 = 359.0f;
        float f3 = this.x + this.radius1;
        float f4 = this.y + this.radius2;
        int n = 360 / this.segmentCount;
        for (float f5 = f; f5 <= f2 + (float)n; f5 += (float)n) {
            float f6 = f5;
            if (f6 > f2) {
                f6 = f2;
            }
            float f7 = (float)((double)f3 + FastTrig.cos(Math.toRadians(f6)) * (double)this.radius1);
            float f8 = (float)((double)f4 + FastTrig.sin(Math.toRadians(f6)) * (double)this.radius2);
            if (f7 > this.maxX) {
                this.maxX = f7;
            }
            if (f8 > this.maxY) {
                this.maxY = f8;
            }
            if (f7 < this.minX) {
                this.minX = f7;
            }
            if (f8 < this.minY) {
                this.minY = f8;
            }
            arrayList.add(new Float(f7));
            arrayList.add(new Float(f8));
        }
        this.points = new float[arrayList.size()];
        for (int i = 0; i < this.points.length; ++i) {
            this.points[i] = ((Float)arrayList.get(i)).floatValue();
        }
    }

    @Override
    public Shape transform(Transform transform) {
        this.checkPoints();
        Polygon polygon = new Polygon();
        float[] fArray = new float[this.points.length];
        transform.transform(this.points, 0, fArray, 0, this.points.length / 2);
        polygon.points = fArray;
        polygon.checkPoints();
        return polygon;
    }

    @Override
    protected void findCenter() {
        this.center = new float[2];
        this.center[0] = this.x + this.radius1;
        this.center[1] = this.y + this.radius2;
    }

    @Override
    protected void calculateRadius() {
        this.boundingCircleRadius = this.radius1 > this.radius2 ? this.radius1 : this.radius2;
    }
}

