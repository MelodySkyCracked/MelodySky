/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.util.FastTrig;

public class RoundedRectangle
extends Rectangle {
    public static final int TOP_LEFT = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_RIGHT = 4;
    public static final int BOTTOM_LEFT = 8;
    public static final int ALL = 15;
    private static final int DEFAULT_SEGMENT_COUNT = 25;
    private float cornerRadius;
    private int segmentCount;
    private int cornerFlags;

    public RoundedRectangle(float f, float f2, float f3, float f4, float f5) {
        this(f, f2, f3, f4, f5, 25);
    }

    public RoundedRectangle(float f, float f2, float f3, float f4, float f5, int n) {
        this(f, f2, f3, f4, f5, n, 15);
    }

    public RoundedRectangle(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        super(f, f2, f3, f4);
        if (f5 < 0.0f) {
            throw new IllegalArgumentException("corner radius must be >= 0");
        }
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.cornerRadius = f5;
        this.segmentCount = n;
        this.pointsDirty = true;
        this.cornerFlags = n2;
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public void setCornerRadius(float f) {
        if (f >= 0.0f && f != this.cornerRadius) {
            this.cornerRadius = f;
            this.pointsDirty = true;
        }
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(float f) {
        if (this.height != f) {
            this.height = f;
            this.pointsDirty = true;
        }
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(float f) {
        if (f != this.width) {
            this.width = f;
            this.pointsDirty = true;
        }
    }

    @Override
    protected void createPoints() {
        this.maxX = this.x + this.width;
        this.maxY = this.y + this.height;
        this.minX = this.x;
        this.minY = this.y;
        float f = this.width - 1.0f;
        float f2 = this.height - 1.0f;
        if (this.cornerRadius == 0.0f) {
            this.points = new float[8];
            this.points[0] = this.x;
            this.points[1] = this.y;
            this.points[2] = this.x + f;
            this.points[3] = this.y;
            this.points[4] = this.x + f;
            this.points[5] = this.y + f2;
            this.points[6] = this.x;
            this.points[7] = this.y + f2;
        } else {
            float f3 = this.cornerRadius * 2.0f;
            if (f3 > f) {
                f3 = f;
                this.cornerRadius = f3 / 2.0f;
            }
            if (f3 > f2) {
                f3 = f2;
                this.cornerRadius = f3 / 2.0f;
            }
            ArrayList<Float> arrayList = new ArrayList<Float>();
            if ((this.cornerFlags & 1) != 0) {
                arrayList.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + this.cornerRadius, 180.0f, 270.0f));
            } else {
                arrayList.add(new Float(this.x));
                arrayList.add(new Float(this.y));
            }
            if ((this.cornerFlags & 2) != 0) {
                arrayList.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + f - this.cornerRadius, this.y + this.cornerRadius, 270.0f, 360.0f));
            } else {
                arrayList.add(new Float(this.x + f));
                arrayList.add(new Float(this.y));
            }
            if ((this.cornerFlags & 4) != 0) {
                arrayList.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + f - this.cornerRadius, this.y + f2 - this.cornerRadius, 0.0f, 90.0f));
            } else {
                arrayList.add(new Float(this.x + f));
                arrayList.add(new Float(this.y + f2));
            }
            if ((this.cornerFlags & 8) != 0) {
                arrayList.addAll(this.createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + f2 - this.cornerRadius, 90.0f, 180.0f));
            } else {
                arrayList.add(new Float(this.x));
                arrayList.add(new Float(this.y + f2));
            }
            this.points = new float[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                this.points[i] = ((Float)arrayList.get(i)).floatValue();
            }
        }
        this.findCenter();
        this.calculateRadius();
    }

    private List createPoints(int n, float f, float f2, float f3, float f4, float f5) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        int n2 = 360 / n;
        for (float f6 = f4; f6 <= f5 + (float)n2; f6 += (float)n2) {
            float f7 = f6;
            if (f7 > f5) {
                f7 = f5;
            }
            float f8 = (float)((double)f2 + FastTrig.cos(Math.toRadians(f7)) * (double)f);
            float f9 = (float)((double)f3 + FastTrig.sin(Math.toRadians(f7)) * (double)f);
            arrayList.add(new Float(f8));
            arrayList.add(new Float(f9));
        }
        return arrayList;
    }

    @Override
    public Shape transform(Transform transform) {
        this.checkPoints();
        Polygon polygon = new Polygon();
        float[] fArray = new float[this.points.length];
        transform.transform(this.points, 0, fArray, 0, this.points.length / 2);
        polygon.points = fArray;
        polygon.findCenter();
        return polygon;
    }
}

