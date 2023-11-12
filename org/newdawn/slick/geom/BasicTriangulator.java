/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.Triangulator;

public class BasicTriangulator
implements Triangulator {
    private static final float EPSILON = 1.0E-10f;
    private PointList poly = new PointList(this);
    private PointList tris = new PointList(this);
    private boolean tried;

    @Override
    public void addPolyPoint(float f, float f2) {
        Point point = new Point(this, f, f2);
        if (!this.poly.contains(point)) {
            this.poly.add(point);
        }
    }

    public int getPolyPointCount() {
        return this.poly.size();
    }

    public float[] getPolyPoint(int n) {
        return new float[]{Point.access$000(this.poly.get(n)), Point.access$100(this.poly.get(n))};
    }

    @Override
    public boolean triangulate() {
        this.tried = true;
        boolean bl = this.process(this.poly, this.tris);
        return bl;
    }

    @Override
    public int getTriangleCount() {
        if (!this.tried) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.tris.size() / 3;
    }

    @Override
    public float[] getTrianglePoint(int n, int n2) {
        if (!this.tried) {
            throw new RuntimeException("Call triangulate() before accessing triangles");
        }
        return this.tris.get(n * 3 + n2).toArray();
    }

    private float area(PointList pointList) {
        int n = pointList.size();
        float f = 0.0f;
        int n2 = n - 1;
        int n3 = 0;
        while (n3 < n) {
            Point point = pointList.get(n2);
            Point point2 = pointList.get(n3);
            f += point.getX() * point2.getY() - point2.getX() * point.getY();
            n2 = n3++;
        }
        return f * 0.5f;
    }

    /*
     * Exception decompiling
     */
    private boolean process(PointList var1, PointList var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl59 : ILOAD - null : trying to set 6 previously set to 0
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
    public void startHole() {
    }

    private class PointList {
        private ArrayList points;
        final BasicTriangulator this$0;

        public PointList(BasicTriangulator basicTriangulator) {
            this.this$0 = basicTriangulator;
            this.points = new ArrayList();
        }

        public boolean contains(Point point) {
            return this.points.contains(point);
        }

        public void add(Point point) {
            this.points.add(point);
        }

        public void remove(Point point) {
            this.points.remove(point);
        }

        public int size() {
            return this.points.size();
        }

        public Point get(int n) {
            return (Point)this.points.get(n);
        }

        public void clear() {
            this.points.clear();
        }
    }

    private class Point {
        private float x;
        private float y;
        private float[] array;
        final BasicTriangulator this$0;

        public Point(BasicTriangulator basicTriangulator, float f, float f2) {
            this.this$0 = basicTriangulator;
            this.x = f;
            this.y = f2;
            this.array = new float[]{f, f2};
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public float[] toArray() {
            return this.array;
        }

        public int hashCode() {
            return (int)(this.x * this.y * 31.0f);
        }

        public boolean equals(Object object) {
            if (object instanceof Point) {
                Point point = (Point)object;
                return point.x == this.x && point.y == this.y;
            }
            return false;
        }

        static float access$000(Point point) {
            return point.x;
        }

        static float access$100(Point point) {
            return point.y;
        }
    }
}

