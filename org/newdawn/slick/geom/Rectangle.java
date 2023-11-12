/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Rectangle
extends Shape {
    protected float width;
    protected float height;

    public Rectangle(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.maxX = f + f3;
        this.maxY = f2 + f4;
        this.checkPoints();
    }

    public boolean contains(float f, float f2) {
        if (f <= this.getX()) {
            return false;
        }
        if (f2 <= this.getY()) {
            return false;
        }
        if (f >= this.maxX) {
            return false;
        }
        return !(f2 >= this.maxY);
    }

    public void setBounds(Rectangle rectangle) {
        this.setBounds(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public void setBounds(float f, float f2, float f3, float f4) {
        this.setX(f);
        this.setY(f2);
        this.setSize(f3, f4);
    }

    public void setSize(float f, float f2) {
        this.setWidth(f);
        this.setHeight(f2);
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    public void grow(float f, float f2) {
        this.setX(this.getX() - f);
        this.setY(this.getY() - f2);
        this.setWidth(this.getWidth() + f * 2.0f);
        this.setHeight(this.getHeight() + f2 * 2.0f);
    }

    public void scaleGrow(float f, float f2) {
        this.grow(this.getWidth() * (f - 1.0f), this.getHeight() * (f2 - 1.0f));
    }

    public void setWidth(float f) {
        if (f != this.width) {
            this.pointsDirty = true;
            this.width = f;
            this.maxX = this.x + f;
        }
    }

    public void setHeight(float f) {
        if (f != this.height) {
            this.pointsDirty = true;
            this.height = f;
            this.maxY = this.y + f;
        }
    }

    public boolean intersects(Shape shape) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle)shape;
            if (this.x > rectangle.x + rectangle.width || this.x + this.width < rectangle.x) {
                return false;
            }
            return !(this.y > rectangle.y + rectangle.height) && !(this.y + this.height < rectangle.y);
        }
        if (shape instanceof Circle) {
            return this.intersects((Circle)shape);
        }
        return super.intersects(shape);
    }

    @Override
    protected void createPoints() {
        float f = this.width;
        float f2 = this.height;
        this.points = new float[8];
        this.points[0] = this.x;
        this.points[1] = this.y;
        this.points[2] = this.x + f;
        this.points[3] = this.y;
        this.points[4] = this.x + f;
        this.points[5] = this.y + f2;
        this.points[6] = this.x;
        this.points[7] = this.y + f2;
        this.maxX = this.points[2];
        this.maxY = this.points[5];
        this.minX = this.points[0];
        this.minY = this.points[1];
        this.findCenter();
        this.calculateRadius();
    }

    private boolean intersects(Circle circle) {
        return circle.intersects((Shape)this);
    }

    public String toString() {
        return "[Rectangle " + this.width + "x" + this.height + "]";
    }

    public static boolean contains(float f, float f2, float f3, float f4, float f5, float f6) {
        return f >= f3 && f2 >= f4 && f <= f3 + f5 && f2 <= f4 + f6;
    }

    @Override
    public Shape transform(Transform transform) {
        this.checkPoints();
        Polygon polygon = new Polygon();
        float[] fArray = new float[this.points.length];
        transform.transform(this.points, 0, fArray, 0, this.points.length / 2);
        polygon.points = fArray;
        polygon.findCenter();
        polygon.checkPoints();
        return polygon;
    }
}

