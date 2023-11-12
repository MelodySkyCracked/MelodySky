/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.Curve;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Path
extends Shape {
    private ArrayList localPoints = new ArrayList();
    private float cx;
    private float cy;
    private boolean closed;
    private ArrayList holes = new ArrayList();
    private ArrayList hole;

    public Path(float f, float f2) {
        this.localPoints.add(new float[]{f, f2});
        this.cx = f;
        this.cy = f2;
        this.pointsDirty = true;
    }

    public void startHole(float f, float f2) {
        this.hole = new ArrayList();
        this.holes.add(this.hole);
    }

    public void lineTo(float f, float f2) {
        if (this.hole != null) {
            this.hole.add(new float[]{f, f2});
        } else {
            this.localPoints.add(new float[]{f, f2});
        }
        this.cx = f;
        this.cy = f2;
        this.pointsDirty = true;
    }

    public void close() {
        this.closed = true;
    }

    public void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        this.curveTo(f, f2, f3, f4, f5, f6, 10);
    }

    public void curveTo(float f, float f2, float f3, float f4, float f5, float f6, int n) {
        if (this.cx == f && this.cy == f2) {
            return;
        }
        Curve curve = new Curve(new Vector2f(this.cx, this.cy), new Vector2f(f3, f4), new Vector2f(f5, f6), new Vector2f(f, f2));
        float f7 = 1.0f / (float)n;
        for (int i = 1; i < n + 1; ++i) {
            float f8 = (float)i * f7;
            Vector2f vector2f = curve.pointAt(f8);
            if (this.hole != null) {
                this.hole.add(new float[]{vector2f.x, vector2f.y});
            } else {
                this.localPoints.add(new float[]{vector2f.x, vector2f.y});
            }
            this.cx = vector2f.x;
            this.cy = vector2f.y;
        }
        this.pointsDirty = true;
    }

    @Override
    protected void createPoints() {
        this.points = new float[this.localPoints.size() * 2];
        for (int i = 0; i < this.localPoints.size(); ++i) {
            float[] fArray = (float[])this.localPoints.get(i);
            this.points[i * 2] = fArray[0];
            this.points[i * 2 + 1] = fArray[1];
        }
    }

    @Override
    public Shape transform(Transform transform) {
        Path path = new Path(this.cx, this.cy);
        path.localPoints = this.transform(this.localPoints, transform);
        for (int i = 0; i < this.holes.size(); ++i) {
            path.holes.add(this.transform((ArrayList)this.holes.get(i), transform));
        }
        path.closed = this.closed;
        return path;
    }

    private ArrayList transform(ArrayList arrayList, Transform transform) {
        float[] fArray = new float[arrayList.size() * 2];
        float[] fArray2 = new float[arrayList.size() * 2];
        for (int i = 0; i < arrayList.size(); ++i) {
            fArray[i * 2] = ((float[])arrayList.get(i))[0];
            fArray[i * 2 + 1] = ((float[])arrayList.get(i))[1];
        }
        transform.transform(fArray, 0, fArray2, 0, arrayList.size());
        ArrayList<float[]> arrayList2 = new ArrayList<float[]>();
        for (int i = 0; i < arrayList.size(); ++i) {
            arrayList2.add(new float[]{fArray2[i * 2], fArray2[i * 2 + 1]});
        }
        return arrayList2;
    }

    @Override
    public boolean closed() {
        return this.closed;
    }
}

