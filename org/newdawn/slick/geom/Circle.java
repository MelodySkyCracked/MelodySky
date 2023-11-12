/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public strictfp class Circle
extends Ellipse {
    public float radius;

    public Circle(float f, float f2, float f3) {
        this(f, f2, f3, 50);
    }

    public Circle(float f, float f2, float f3, int n) {
        super(f, f2, f3, f3, n);
        this.x = f - f3;
        this.y = f2 - f3;
        this.radius = f3;
        this.boundingCircleRadius = f3;
    }

    @Override
    public float getCenterX() {
        return this.getX() + this.radius;
    }

    @Override
    public float getCenterY() {
        return this.getY() + this.radius;
    }

    @Override
    public float[] getCenter() {
        return new float[]{this.getCenterX(), this.getCenterY()};
    }

    public void setRadius(float f) {
        if (f != this.radius) {
            this.pointsDirty = true;
            this.radius = f;
            this.setRadii(f, f);
        }
    }

    public float getRadius() {
        return this.radius;
    }

    public boolean intersects(Shape shape) {
        if (shape instanceof Circle) {
            float f;
            Circle circle = (Circle)shape;
            float f2 = this.getRadius() + circle.getRadius();
            if (Math.abs(circle.getCenterX() - this.getCenterX()) > f2) {
                return false;
            }
            if (Math.abs(circle.getCenterY() - this.getCenterY()) > f2) {
                return false;
            }
            float f3 = Math.abs(circle.getCenterX() - this.getCenterX());
            return (f2 *= f2) >= f3 * f3 + (f = Math.abs(circle.getCenterY() - this.getCenterY())) * f;
        }
        if (shape instanceof Rectangle) {
            return this.intersects((Rectangle)shape);
        }
        return super.intersects(shape);
    }

    /*
     * Exception decompiling
     */
    private boolean contains(Line var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl15 : ICONST_0 - null : trying to set 2 previously set to 4
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    protected void findCenter() {
        this.center = new float[2];
        this.center[0] = this.x + this.radius;
        this.center[1] = this.y + this.radius;
    }

    @Override
    protected void calculateRadius() {
        this.boundingCircleRadius = this.radius;
    }

    private boolean intersects(Rectangle rectangle) {
        Rectangle rectangle2 = rectangle;
        Circle circle = this;
        if (rectangle2.contains(this.x + this.radius, this.y + this.radius)) {
            return true;
        }
        float f = rectangle2.getX();
        float f2 = rectangle2.getY();
        float f3 = rectangle2.getX() + rectangle2.getWidth();
        float f4 = rectangle2.getY() + rectangle2.getHeight();
        Line[] lineArray = new Line[]{new Line(f, f2, f3, f2), new Line(f3, f2, f3, f4), new Line(f3, f4, f, f4), new Line(f, f4, f, f2)};
        float f5 = circle.getRadius() * circle.getRadius();
        Vector2f vector2f = new Vector2f(circle.getCenterX(), circle.getCenterY());
        for (int i = 0; i < 4; ++i) {
            float f6 = lineArray[i].distanceSquared(vector2f);
            if (!(f6 < f5)) continue;
            return true;
        }
        return false;
    }

    private boolean intersects(Line line) {
        Vector2f vector2f;
        Vector2f vector2f2 = new Vector2f(line.getX1(), line.getY1());
        Vector2f vector2f3 = new Vector2f(line.getX2(), line.getY2());
        Vector2f vector2f4 = new Vector2f(this.getCenterX(), this.getCenterY());
        Vector2f vector2f5 = vector2f3.copy().sub(vector2f2);
        Vector2f vector2f6 = vector2f4.copy().sub(vector2f2);
        float f = vector2f5.length();
        float f2 = vector2f6.dot(vector2f5) / f;
        if (f2 < 0.0f) {
            vector2f = vector2f2;
        } else if (f2 > f) {
            vector2f = vector2f3;
        } else {
            Vector2f vector2f7 = vector2f5.copy().scale(f2 / f);
            vector2f = vector2f2.copy().add(vector2f7);
        }
        boolean bl = vector2f4.copy().sub(vector2f).lengthSquared() <= this.getRadius() * this.getRadius();
        return bl;
    }
}

