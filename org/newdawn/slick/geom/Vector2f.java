/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.io.Serializable;
import org.newdawn.slick.util.FastTrig;

public strictfp class Vector2f
implements Serializable {
    private static final long serialVersionUID = 1339934L;
    public float x;
    public float y;

    public Vector2f() {
    }

    public Vector2f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
    }

    public Vector2f(double d) {
        this.x = 1.0f;
        this.y = 0.0f;
        this.setTheta(d);
    }

    public void setTheta(double d) {
        if (d < -360.0 || d > 360.0) {
            d %= 360.0;
        }
        if (d < 0.0) {
            d = 360.0 + d;
        }
        double d2 = this.getTheta();
        if (d < -360.0 || d > 360.0) {
            d2 %= 360.0;
        }
        if (d < 0.0) {
            d2 = 360.0 + d2;
        }
        float f = this.length();
        this.x = f * (float)FastTrig.cos(StrictMath.toRadians(d));
        this.y = f * (float)FastTrig.sin(StrictMath.toRadians(d));
    }

    public Vector2f add(double d) {
        this.setTheta(this.getTheta() + d);
        return this;
    }

    public Vector2f sub(double d) {
        this.setTheta(this.getTheta() - d);
        return this;
    }

    public double getTheta() {
        double d = StrictMath.toDegrees(StrictMath.atan2(this.y, this.x));
        if (d < -360.0 || d > 360.0) {
            d %= 360.0;
        }
        if (d < 0.0) {
            d = 360.0 + d;
        }
        return d;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public Vector2f(Vector2f vector2f) {
        this(vector2f.getX(), vector2f.getY());
    }

    public Vector2f(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void set(Vector2f vector2f) {
        this.set(vector2f.getX(), vector2f.getY());
    }

    public float dot(Vector2f vector2f) {
        return this.x * vector2f.getX() + this.y * vector2f.getY();
    }

    public Vector2f set(float f, float f2) {
        this.x = f;
        this.y = f2;
        return this;
    }

    public Vector2f getPerpendicular() {
        return new Vector2f(-this.y, this.x);
    }

    public Vector2f set(float[] fArray) {
        return this.set(fArray[0], fArray[1]);
    }

    public Vector2f negate() {
        return new Vector2f(-this.x, -this.y);
    }

    public Vector2f negateLocal() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public Vector2f add(Vector2f vector2f) {
        this.x += vector2f.getX();
        this.y += vector2f.getY();
        return this;
    }

    public Vector2f sub(Vector2f vector2f) {
        this.x -= vector2f.getX();
        this.y -= vector2f.getY();
        return this;
    }

    public Vector2f scale(float f) {
        this.x *= f;
        this.y *= f;
        return this;
    }

    public Vector2f normalise() {
        float f = this.length();
        if (f == 0.0f) {
            return this;
        }
        this.x /= f;
        this.y /= f;
        return this;
    }

    public Vector2f getNormal() {
        Vector2f vector2f = this.copy();
        vector2f.normalise();
        return vector2f;
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public float length() {
        return (float)Math.sqrt(this.lengthSquared());
    }

    public void projectOntoUnit(Vector2f vector2f, Vector2f vector2f2) {
        float f = vector2f.dot(this);
        vector2f2.x = f * vector2f.getX();
        vector2f2.y = f * vector2f.getY();
    }

    public Vector2f copy() {
        return new Vector2f(this.x, this.y);
    }

    public String toString() {
        return "[Vector2f " + this.x + "," + this.y + " (" + this.length() + ")]";
    }

    public float distance(Vector2f vector2f) {
        return (float)Math.sqrt(this.distanceSquared(vector2f));
    }

    public float distanceSquared(Vector2f vector2f) {
        float f = vector2f.getX() - this.getX();
        float f2 = vector2f.getY() - this.getY();
        return f * f + f2 * f2;
    }

    public int hashCode() {
        return 997 * (int)this.x ^ 991 * (int)this.y;
    }

    public boolean equals(Object object) {
        if (object instanceof Vector2f) {
            Vector2f vector2f = (Vector2f)object;
            return vector2f.x == this.x && vector2f.y == this.y;
        }
        return false;
    }
}

