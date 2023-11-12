/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Polygon
extends Shape {
    private boolean allowDups = false;
    private boolean closed = true;

    public Polygon(float[] fArray) {
        int n = fArray.length;
        this.points = new float[n];
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
        this.x = Float.MAX_VALUE;
        this.y = Float.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            this.points[i] = fArray[i];
            if (i % 2 == 0) {
                if (fArray[i] > this.maxX) {
                    this.maxX = fArray[i];
                }
                if (fArray[i] < this.minX) {
                    this.minX = fArray[i];
                }
                if (!(fArray[i] < this.x)) continue;
                this.x = fArray[i];
                continue;
            }
            if (fArray[i] > this.maxY) {
                this.maxY = fArray[i];
            }
            if (fArray[i] < this.minY) {
                this.minY = fArray[i];
            }
            if (!(fArray[i] < this.y)) continue;
            this.y = fArray[i];
        }
        this.findCenter();
        this.calculateRadius();
        this.pointsDirty = true;
    }

    public Polygon() {
        this.points = new float[0];
        this.maxX = -1.4E-45f;
        this.maxY = -1.4E-45f;
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
    }

    public void setAllowDuplicatePoints(boolean bl) {
        this.allowDups = bl;
    }

    public void addPoint(float f, float f2) {
        int n;
        if (this.hasVertex(f, f2) && !this.allowDups) {
            return;
        }
        ArrayList<Float> arrayList = new ArrayList<Float>();
        for (n = 0; n < this.points.length; ++n) {
            arrayList.add(new Float(this.points[n]));
        }
        arrayList.add(new Float(f));
        arrayList.add(new Float(f2));
        n = arrayList.size();
        this.points = new float[n];
        for (int i = 0; i < n; ++i) {
            this.points[i] = ((Float)arrayList.get(i)).floatValue();
        }
        if (f > this.maxX) {
            this.maxX = f;
        }
        if (f2 > this.maxY) {
            this.maxY = f2;
        }
        if (f < this.minX) {
            this.minX = f;
        }
        if (f2 < this.minY) {
            this.minY = f2;
        }
        this.findCenter();
        this.calculateRadius();
        this.pointsDirty = true;
    }

    @Override
    public Shape transform(Transform transform) {
        this.checkPoints();
        Polygon polygon = new Polygon();
        float[] fArray = new float[this.points.length];
        transform.transform(this.points, 0, fArray, 0, this.points.length / 2);
        polygon.points = fArray;
        polygon.findCenter();
        polygon.closed = this.closed;
        return polygon;
    }

    @Override
    public void setX(float f) {
        super.setX(f);
        this.pointsDirty = false;
    }

    @Override
    public void setY(float f) {
        super.setY(f);
        this.pointsDirty = false;
    }

    @Override
    protected void createPoints() {
    }

    @Override
    public boolean closed() {
        return this.closed;
    }

    public void setClosed(boolean bl) {
        this.closed = bl;
    }

    public Polygon copy() {
        float[] fArray = new float[this.points.length];
        System.arraycopy(this.points, 0, fArray, 0, fArray.length);
        return new Polygon(fArray);
    }
}

