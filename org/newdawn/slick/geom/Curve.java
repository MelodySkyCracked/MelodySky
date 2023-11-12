/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class Curve
extends Shape {
    private Vector2f p1;
    private Vector2f c1;
    private Vector2f c2;
    private Vector2f p2;
    private int segments;

    public Curve(Vector2f vector2f, Vector2f vector2f2, Vector2f vector2f3, Vector2f vector2f4) {
        this(vector2f, vector2f2, vector2f3, vector2f4, 20);
    }

    public Curve(Vector2f vector2f, Vector2f vector2f2, Vector2f vector2f3, Vector2f vector2f4, int n) {
        this.p1 = new Vector2f(vector2f);
        this.c1 = new Vector2f(vector2f2);
        this.c2 = new Vector2f(vector2f3);
        this.p2 = new Vector2f(vector2f4);
        this.segments = n;
        this.pointsDirty = true;
    }

    public Vector2f pointAt(float f) {
        float f2 = 1.0f - f;
        float f3 = f;
        float f4 = f2 * f2 * f2;
        float f5 = 3.0f * f2 * f2 * f3;
        float f6 = 3.0f * f2 * f3 * f3;
        float f7 = f3 * f3 * f3;
        float f8 = this.p1.x * f4 + this.c1.x * f5 + this.c2.x * f6 + this.p2.x * f7;
        float f9 = this.p1.y * f4 + this.c1.y * f5 + this.c2.y * f6 + this.p2.y * f7;
        return new Vector2f(f8, f9);
    }

    @Override
    protected void createPoints() {
        float f = 1.0f / (float)this.segments;
        this.points = new float[(this.segments + 1) * 2];
        for (int i = 0; i < this.segments + 1; ++i) {
            float f2 = (float)i * f;
            Vector2f vector2f = this.pointAt(f2);
            this.points[i * 2] = vector2f.x;
            this.points[i * 2 + 1] = vector2f.y;
        }
    }

    @Override
    public Shape transform(Transform transform) {
        float[] fArray = new float[8];
        float[] fArray2 = new float[8];
        fArray[0] = this.p1.x;
        fArray[1] = this.p1.y;
        fArray[2] = this.c1.x;
        fArray[3] = this.c1.y;
        fArray[4] = this.c2.x;
        fArray[5] = this.c2.y;
        fArray[6] = this.p2.x;
        fArray[7] = this.p2.y;
        transform.transform(fArray, 0, fArray2, 0, 4);
        return new Curve(new Vector2f(fArray2[0], fArray2[1]), new Vector2f(fArray2[2], fArray2[3]), new Vector2f(fArray2[4], fArray2[5]), new Vector2f(fArray2[6], fArray2[7]));
    }

    @Override
    public boolean closed() {
        return false;
    }
}

