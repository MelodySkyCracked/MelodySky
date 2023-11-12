/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Line
extends Shape {
    private Vector2f start;
    private Vector2f end;
    private Vector2f vec;
    private float lenSquared;
    private Vector2f loc = new Vector2f(0.0f, 0.0f);
    private Vector2f v = new Vector2f(0.0f, 0.0f);
    private Vector2f v2 = new Vector2f(0.0f, 0.0f);
    private Vector2f proj = new Vector2f(0.0f, 0.0f);
    private Vector2f closest = new Vector2f(0.0f, 0.0f);
    private Vector2f other = new Vector2f(0.0f, 0.0f);
    private boolean outerEdge = true;
    private boolean innerEdge = true;

    public Line(float f, float f2, boolean bl, boolean bl2) {
        this(0.0f, 0.0f, f, f2);
    }

    public Line(float f, float f2) {
        this(f, f2, true, true);
    }

    public Line(float f, float f2, float f3, float f4) {
        this(new Vector2f(f, f2), new Vector2f(f3, f4));
    }

    public Line(float f, float f2, float f3, float f4, boolean bl) {
        this(new Vector2f(f, f2), new Vector2f(f + f3, f2 + f4));
    }

    public Line(float[] fArray, float[] fArray2) {
        this.set(fArray, fArray2);
    }

    public Line(Vector2f vector2f, Vector2f vector2f2) {
        this.set(vector2f, vector2f2);
    }

    public void set(float[] fArray, float[] fArray2) {
        this.set(fArray[0], fArray[1], fArray2[0], fArray2[1]);
    }

    public Vector2f getStart() {
        return this.start;
    }

    public Vector2f getEnd() {
        return this.end;
    }

    public float length() {
        return this.vec.length();
    }

    public float lengthSquared() {
        return this.vec.lengthSquared();
    }

    public void set(Vector2f vector2f, Vector2f vector2f2) {
        this.pointsDirty = true;
        if (this.start == null) {
            this.start = new Vector2f();
        }
        this.start.set(vector2f);
        if (this.end == null) {
            this.end = new Vector2f();
        }
        this.end.set(vector2f2);
        this.vec = new Vector2f(vector2f2);
        this.vec.sub(vector2f);
        this.lenSquared = this.vec.lengthSquared();
    }

    public void set(float f, float f2, float f3, float f4) {
        this.pointsDirty = true;
        this.start.set(f, f2);
        this.end.set(f3, f4);
        float f5 = f3 - f;
        float f6 = f4 - f2;
        this.vec.set(f5, f6);
        this.lenSquared = f5 * f5 + f6 * f6;
    }

    public float getDX() {
        return this.end.getX() - this.start.getX();
    }

    public float getDY() {
        return this.end.getY() - this.start.getY();
    }

    @Override
    public float getX() {
        return this.getX1();
    }

    @Override
    public float getY() {
        return this.getY1();
    }

    public float getX1() {
        return this.start.getX();
    }

    public float getY1() {
        return this.start.getY();
    }

    public float getX2() {
        return this.end.getX();
    }

    public float getY2() {
        return this.end.getY();
    }

    public float distance(Vector2f vector2f) {
        return (float)Math.sqrt(this.distanceSquared(vector2f));
    }

    public boolean on(Vector2f vector2f) {
        this.getClosestPoint(vector2f, this.closest);
        return vector2f.equals(this.closest);
    }

    public float distanceSquared(Vector2f vector2f) {
        this.getClosestPoint(vector2f, this.closest);
        this.closest.sub(vector2f);
        float f = this.closest.lengthSquared();
        return f;
    }

    public void getClosestPoint(Vector2f vector2f, Vector2f vector2f2) {
        this.loc.set(vector2f);
        this.loc.sub(this.start);
        float f = this.vec.dot(this.loc);
        f /= this.vec.lengthSquared();
        if (f < 0.0f) {
            vector2f2.set(this.start);
            return;
        }
        if (f > 1.0f) {
            vector2f2.set(this.end);
            return;
        }
        vector2f2.x = this.start.getX() + f * this.vec.getX();
        vector2f2.y = this.start.getY() + f * this.vec.getY();
    }

    public String toString() {
        return "[Line " + this.start + "," + this.end + "]";
    }

    public Vector2f intersect(Line line) {
        return this.intersect(line, false);
    }

    public Vector2f intersect(Line line, boolean bl) {
        Vector2f vector2f = new Vector2f();
        Line line2 = this;
        Line line3 = line;
        boolean bl2 = bl;
        if (vector2f == false) {
            return null;
        }
        return vector2f;
    }

    @Override
    protected void createPoints() {
        this.points = new float[4];
        this.points[0] = this.getX1();
        this.points[1] = this.getY1();
        this.points[2] = this.getX2();
        this.points[3] = this.getY2();
    }

    @Override
    public Shape transform(Transform transform) {
        float[] fArray = new float[4];
        this.createPoints();
        transform.transform(this.points, 0, fArray, 0, 2);
        return new Line(fArray[0], fArray[1], fArray[2], fArray[3]);
    }

    @Override
    public boolean closed() {
        return false;
    }

    public boolean intersects(Shape shape) {
        if (shape instanceof Circle) {
            return shape.intersects(this);
        }
        return super.intersects(shape);
    }
}

