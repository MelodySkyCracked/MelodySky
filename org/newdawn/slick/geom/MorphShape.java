/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class MorphShape
extends Shape {
    private ArrayList shapes = new ArrayList();
    private float offset;
    private Shape current;
    private Shape next;

    public MorphShape(Shape shape) {
        this.shapes.add(shape);
        float[] fArray = shape.points;
        this.points = new float[fArray.length];
        this.current = shape;
        this.next = shape;
    }

    public void addShape(Shape shape) {
        if (shape.points.length != this.points.length) {
            throw new RuntimeException("Attempt to morph between two shapes with different vertex counts");
        }
        Shape shape2 = (Shape)this.shapes.get(this.shapes.size() - 1);
        MorphShape morphShape = this;
        if (shape2 < shape) {
            this.shapes.add(shape2);
        } else {
            this.shapes.add(shape);
        }
        if (this.shapes.size() == 2) {
            this.next = (Shape)this.shapes.get(1);
        }
    }

    public void setMorphTime(float f) {
        int n = (int)f;
        int n2 = n + 1;
        float f2 = f - (float)n;
        n = this.rational(n);
        n2 = this.rational(n2);
        this.setFrame(n, n2, f2);
    }

    public void updateMorphTime(float f) {
        this.offset += f;
        if (this.offset < 0.0f) {
            int n = this.shapes.indexOf(this.current);
            if (n < 0) {
                n = this.shapes.size() - 1;
            }
            int n2 = this.rational(n + 1);
            this.setFrame(n, n2, this.offset);
            this.offset += 1.0f;
        } else if (this.offset > 1.0f) {
            int n = this.shapes.indexOf(this.next);
            if (n < 1) {
                n = 0;
            }
            int n3 = this.rational(n + 1);
            this.setFrame(n, n3, this.offset);
            this.offset -= 1.0f;
        } else {
            this.pointsDirty = true;
        }
    }

    public void setExternalFrame(Shape shape) {
        this.current = shape;
        this.next = (Shape)this.shapes.get(0);
        this.offset = 0.0f;
    }

    private int rational(int n) {
        while (n >= this.shapes.size()) {
            n -= this.shapes.size();
        }
        while (n < 0) {
            n += this.shapes.size();
        }
        return n;
    }

    private void setFrame(int n, int n2, float f) {
        this.current = (Shape)this.shapes.get(n);
        this.next = (Shape)this.shapes.get(n2);
        this.offset = f;
        this.pointsDirty = true;
    }

    @Override
    protected void createPoints() {
        if (this.current == this.next) {
            System.arraycopy(this.current.points, 0, this.points, 0, this.points.length);
            return;
        }
        float[] fArray = this.current.points;
        float[] fArray2 = this.next.points;
        for (int i = 0; i < this.points.length; ++i) {
            this.points[i] = fArray[i] * (1.0f - this.offset);
            int n = i;
            this.points[n] = this.points[n] + fArray2[i] * this.offset;
        }
    }

    @Override
    public Shape transform(Transform transform) {
        this.createPoints();
        Polygon polygon = new Polygon(this.points);
        return polygon;
    }
}

