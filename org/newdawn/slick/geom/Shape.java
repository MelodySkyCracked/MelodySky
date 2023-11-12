/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.io.Serializable;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.NeatTriangulator;
import org.newdawn.slick.geom.OverTriangulator;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Triangulator;
import org.newdawn.slick.geom.Vector2f;

public abstract class Shape
implements Serializable {
    protected float[] points;
    protected float[] center;
    protected float x;
    protected float y;
    protected float maxX;
    protected float maxY;
    protected float minX;
    protected float minY;
    protected float boundingCircleRadius;
    protected boolean pointsDirty = true;
    protected transient Triangulator tris;
    protected boolean trianglesDirty;

    public void setLocation(float f, float f2) {
        this.setX(f);
        this.setY(f2);
    }

    public abstract Shape transform(Transform var1);

    protected abstract void createPoints();

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        if (f != this.x) {
            float f2 = f - this.x;
            this.x = f;
            if (this.points == null || this.center == null) {
                this.checkPoints();
            }
            for (int i = 0; i < this.points.length / 2; ++i) {
                int n = i * 2;
                this.points[n] = this.points[n] + f2;
            }
            this.center[0] = this.center[0] + f2;
            f += f2;
            this.maxX += f2;
            this.minX += f2;
            this.trianglesDirty = true;
        }
    }

    public void setY(float f) {
        if (f != this.y) {
            float f2 = f - this.y;
            this.y = f;
            if (this.points == null || this.center == null) {
                this.checkPoints();
            }
            for (int i = 0; i < this.points.length / 2; ++i) {
                int n = i * 2 + 1;
                this.points[n] = this.points[n] + f2;
            }
            this.center[1] = this.center[1] + f2;
            f += f2;
            this.maxY += f2;
            this.minY += f2;
            this.trianglesDirty = true;
        }
    }

    public float getY() {
        return this.y;
    }

    public Vector2f getLocation() {
        return new Vector2f(this.getX(), this.getY());
    }

    public void setLocation(Vector2f vector2f) {
        this.setX(vector2f.x);
        this.setY(vector2f.y);
    }

    public float getCenterX() {
        this.checkPoints();
        return this.center[0];
    }

    public void setCenterX(float f) {
        if (this.points == null || this.center == null) {
            this.checkPoints();
        }
        float f2 = f - this.getCenterX();
        this.setX(this.x + f2);
    }

    public float getCenterY() {
        this.checkPoints();
        return this.center[1];
    }

    public void setCenterY(float f) {
        if (this.points == null || this.center == null) {
            this.checkPoints();
        }
        float f2 = f - this.getCenterY();
        this.setY(this.y + f2);
    }

    public float getMaxX() {
        this.checkPoints();
        return this.maxX;
    }

    public float getMaxY() {
        this.checkPoints();
        return this.maxY;
    }

    public float getMinX() {
        this.checkPoints();
        return this.minX;
    }

    public float getMinY() {
        this.checkPoints();
        return this.minY;
    }

    public float getBoundingCircleRadius() {
        this.checkPoints();
        return this.boundingCircleRadius;
    }

    public float[] getCenter() {
        this.checkPoints();
        return this.center;
    }

    public float[] getPoints() {
        this.checkPoints();
        return this.points;
    }

    public int getPointCount() {
        this.checkPoints();
        return this.points.length / 2;
    }

    public float[] getPoint(int n) {
        this.checkPoints();
        float[] fArray = new float[]{this.points[n * 2], this.points[n * 2 + 1]};
        return fArray;
    }

    public float[] getNormal(int n) {
        float[] fArray = this.getPoint(n);
        float[] fArray2 = this.getPoint(n - 1 < 0 ? this.getPointCount() - 1 : n - 1);
        float[] fArray3 = this.getPoint(n + 1 >= this.getPointCount() ? 0 : n + 1);
        float[] fArray4 = this.getNormal(fArray2, fArray);
        float[] fArray5 = this.getNormal(fArray, fArray3);
        if (n == 0 && !this.closed()) {
            return fArray5;
        }
        if (n == this.getPointCount() - 1 && !this.closed()) {
            return fArray4;
        }
        float f = (fArray4[0] + fArray5[0]) / 2.0f;
        float f2 = (fArray4[1] + fArray5[1]) / 2.0f;
        float f3 = (float)Math.sqrt(f * f + f2 * f2);
        return new float[]{f / f3, f2 / f3};
    }

    /*
     * Exception decompiling
     */
    public boolean contains(Shape var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl8 : ILOAD_2 - null : trying to set 3 previously set to 1
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

    private float[] getNormal(float[] fArray, float[] fArray2) {
        float f = fArray[0] - fArray2[0];
        float f2 = fArray[1] - fArray2[1];
        float f3 = (float)Math.sqrt(f * f + f2 * f2);
        return new float[]{-(f2 /= f3), f /= f3};
    }

    public boolean includes(float f, float f2) {
        if (this.points.length == 0) {
            return false;
        }
        this.checkPoints();
        Line line = new Line(0.0f, 0.0f, 0.0f, 0.0f);
        Vector2f vector2f = new Vector2f(f, f2);
        for (int i = 0; i < this.points.length; i += 2) {
            int n = i + 2;
            if (n >= this.points.length) {
                n = 0;
            }
            line.set(this.points[i], this.points[i + 1], this.points[n], this.points[n + 1]);
            if (!line.on(vector2f)) continue;
            return true;
        }
        return false;
    }

    public int indexOf(float f, float f2) {
        for (int i = 0; i < this.points.length; i += 2) {
            if (this.points[i] != f || this.points[i + 1] != f2) continue;
            return i / 2;
        }
        return -1;
    }

    public boolean hasVertex(float f, float f2) {
        if (this.points.length == 0) {
            return false;
        }
        this.checkPoints();
        for (int i = 0; i < this.points.length; i += 2) {
            if (this.points[i] != f || this.points[i + 1] != f2) continue;
            return true;
        }
        return false;
    }

    protected void findCenter() {
        this.center = new float[]{0.0f, 0.0f};
        int n = this.points.length;
        for (int i = 0; i < n; i += 2) {
            this.center[0] = this.center[0] + this.points[i];
            this.center[1] = this.center[1] + this.points[i + 1];
        }
        this.center[0] = this.center[0] / (float)(n / 2);
        this.center[1] = this.center[1] / (float)(n / 2);
    }

    protected void calculateRadius() {
        this.boundingCircleRadius = 0.0f;
        for (int i = 0; i < this.points.length; i += 2) {
            float f = (this.points[i] - this.center[0]) * (this.points[i] - this.center[0]) + (this.points[i + 1] - this.center[1]) * (this.points[i + 1] - this.center[1]);
            this.boundingCircleRadius = this.boundingCircleRadius > f ? this.boundingCircleRadius : f;
        }
        this.boundingCircleRadius = (float)Math.sqrt(this.boundingCircleRadius);
    }

    protected void calculateTriangles() {
        if (!this.trianglesDirty && this.tris != null) {
            return;
        }
        if (this.points.length >= 6) {
            int n;
            boolean bl = true;
            float f = 0.0f;
            for (n = 0; n < this.points.length / 2 - 1; ++n) {
                float f2 = this.points[n * 2];
                float f3 = this.points[n * 2 + 1];
                float f4 = this.points[n * 2 + 2];
                float f5 = this.points[n * 2 + 3];
                f += f2 * f5 - f3 * f4;
            }
            bl = (f /= 2.0f) > 0.0f;
            this.tris = new NeatTriangulator();
            for (n = 0; n < this.points.length; n += 2) {
                this.tris.addPolyPoint(this.points[n], this.points[n + 1]);
            }
            this.tris.triangulate();
        }
        this.trianglesDirty = false;
    }

    public void increaseTriangulation() {
        this.checkPoints();
        this.calculateTriangles();
        this.tris = new OverTriangulator(this.tris);
    }

    public Triangulator getTriangles() {
        this.checkPoints();
        this.calculateTriangles();
        return this.tris;
    }

    protected final void checkPoints() {
        if (this.pointsDirty) {
            this.createPoints();
            this.findCenter();
            this.calculateRadius();
            if (this.points.length > 0) {
                this.maxX = this.points[0];
                this.maxY = this.points[1];
                this.minX = this.points[0];
                this.minY = this.points[1];
                for (int i = 0; i < this.points.length / 2; ++i) {
                    this.maxX = Math.max(this.points[i * 2], this.maxX);
                    this.maxY = Math.max(this.points[i * 2 + 1], this.maxY);
                    this.minX = Math.min(this.points[i * 2], this.minX);
                    this.minY = Math.min(this.points[i * 2 + 1], this.minY);
                }
            }
            this.pointsDirty = false;
            this.trianglesDirty = true;
        }
    }

    public void preCache() {
        this.checkPoints();
        this.getTriangles();
    }

    public boolean closed() {
        return true;
    }

    public Shape prune() {
        Polygon polygon = new Polygon();
        for (int i = 0; i < this.getPointCount(); ++i) {
            float f;
            float f2;
            int n = i + 1 >= this.getPointCount() ? 0 : i + 1;
            int n2 = i - 1 < 0 ? this.getPointCount() - 1 : i - 1;
            float f3 = this.getPoint(i)[0] - this.getPoint(n2)[0];
            float f4 = this.getPoint(i)[1] - this.getPoint(n2)[1];
            float f5 = this.getPoint(n)[0] - this.getPoint(i)[0];
            float f6 = this.getPoint(n)[1] - this.getPoint(i)[1];
            if ((f3 /= (f2 = (float)Math.sqrt(f3 * f3 + f4 * f4))) == (f5 /= (f = (float)Math.sqrt(f5 * f5 + f6 * f6))) && (f4 /= f2) == (f6 /= f)) continue;
            polygon.addPoint(this.getPoint(i)[0], this.getPoint(i)[1]);
        }
        return polygon;
    }

    public Shape[] subtract(Shape shape) {
        return new GeomUtil().subtract(this, shape);
    }

    public Shape[] union(Shape shape) {
        return new GeomUtil().union(this, shape);
    }

    public float getWidth() {
        return this.maxX - this.minX;
    }

    public float getHeight() {
        return this.maxY - this.minY;
    }
}

