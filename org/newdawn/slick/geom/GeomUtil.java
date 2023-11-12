/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.util.ArrayList;
import org.newdawn.slick.geom.GeomUtilListener;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class GeomUtil {
    public float EPSILON = 1.0E-4f;
    public float EDGE_SCALE = 1.0f;
    public int MAX_POINTS = 10000;
    public GeomUtilListener listener;

    /*
     * Exception decompiling
     */
    public Shape[] subtract(Shape var1, Shape var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl59 : ILOAD - null : trying to set 2 previously set to 0
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

    public void setListener(GeomUtilListener geomUtilListener) {
        this.listener = geomUtilListener;
    }

    public Shape[] union(Shape shape, Shape shape2) {
        int n;
        if (!(shape = shape.transform(new Transform())).intersects(shape2 = shape2.transform(new Transform()))) {
            return new Shape[]{shape, shape2};
        }
        boolean bl = false;
        int n2 = 0;
        for (n = 0; n < shape.getPointCount(); ++n) {
            if (shape2.contains(shape.getPoint(n)[0], shape.getPoint(n)[1]) && !shape2.hasVertex(shape.getPoint(n)[0], shape.getPoint(n)[1])) {
                bl = true;
                break;
            }
            if (!shape2.hasVertex(shape.getPoint(n)[0], shape.getPoint(n)[1])) continue;
            ++n2;
        }
        for (n = 0; n < shape2.getPointCount(); ++n) {
            if (!shape.contains(shape2.getPoint(n)[0], shape2.getPoint(n)[1]) || shape.hasVertex(shape2.getPoint(n)[0], shape2.getPoint(n)[1])) continue;
            bl = true;
            break;
        }
        if (!bl && n2 < 2) {
            return new Shape[]{shape, shape2};
        }
        return this.combine(shape, shape2, false);
    }

    private Shape[] combine(Shape shape, Shape shape2, boolean bl) {
        if (bl) {
            float[] fArray;
            int n;
            ArrayList<Shape> arrayList = new ArrayList<Shape>();
            ArrayList<Vector2f> arrayList2 = new ArrayList<Vector2f>();
            for (n = 0; n < shape.getPointCount(); ++n) {
                fArray = shape.getPoint(n);
                if (!shape2.contains(fArray[0], fArray[1])) continue;
                arrayList2.add(new Vector2f(fArray[0], fArray[1]));
                if (this.listener == null) continue;
                this.listener.pointExcluded(fArray[0], fArray[1]);
            }
            for (n = 0; n < shape.getPointCount(); ++n) {
                fArray = shape.getPoint(n);
                Vector2f vector2f = new Vector2f(fArray[0], fArray[1]);
                if (arrayList2.contains(vector2f)) continue;
                Shape shape3 = this.combineSingle(shape, shape2, true, n);
                arrayList.add(shape3);
                for (int i = 0; i < shape3.getPointCount(); ++i) {
                    float[] fArray2 = shape3.getPoint(i);
                    Vector2f vector2f2 = new Vector2f(fArray2[0], fArray2[1]);
                    arrayList2.add(vector2f2);
                }
            }
            return arrayList.toArray(new Shape[0]);
        }
        for (int i = 0; i < shape.getPointCount(); ++i) {
            if (shape2.contains(shape.getPoint(i)[0], shape.getPoint(i)[1]) || shape2.hasVertex(shape.getPoint(i)[0], shape.getPoint(i)[1])) continue;
            Shape shape4 = this.combineSingle(shape, shape2, false, i);
            return new Shape[]{shape4};
        }
        return new Shape[]{shape2};
    }

    private Shape combineSingle(Shape shape, Shape shape2, boolean bl, int n) {
        Shape shape3 = shape;
        Shape shape4 = shape2;
        int n2 = n;
        int n3 = 1;
        Polygon polygon = new Polygon();
        boolean bl2 = true;
        int n4 = 0;
        float f = shape3.getPoint(n2)[0];
        float f2 = shape3.getPoint(n2)[1];
        while (!polygon.hasVertex(f, f2) || bl2 || shape3 != shape) {
            Line line;
            HitResult hitResult;
            bl2 = false;
            if (++n4 > this.MAX_POINTS) break;
            polygon.addPoint(f, f2);
            if (this.listener != null) {
                this.listener.pointUsed(f, f2);
            }
            if ((hitResult = this.intersect(shape4, line = this.getLine(shape3, f, f2, GeomUtil.rationalPoint(shape3, n2 + n3)))) != null) {
                Shape shape5;
                Line line2 = hitResult.line;
                Vector2f vector2f = hitResult.pt;
                f = vector2f.x;
                f2 = vector2f.y;
                if (this.listener != null) {
                    this.listener.pointIntersected(f, f2);
                }
                if (shape4.hasVertex(f, f2)) {
                    n2 = shape4.indexOf(vector2f.x, vector2f.y);
                    n3 = 1;
                    f = vector2f.x;
                    f2 = vector2f.y;
                    Shape shape6 = shape3;
                    shape3 = shape4;
                    shape4 = shape6;
                    continue;
                }
                float f3 = line2.getDX() / line2.length();
                float f4 = line2.getDY() / line2.length();
                if (shape3.contains(vector2f.x + (f3 *= this.EDGE_SCALE), vector2f.y + (f4 *= this.EDGE_SCALE))) {
                    if (bl) {
                        if (shape3 == shape2) {
                            n2 = hitResult.p2;
                            n3 = -1;
                        } else {
                            n2 = hitResult.p1;
                            n3 = 1;
                        }
                    } else if (shape3 == shape) {
                        n2 = hitResult.p2;
                        n3 = -1;
                    } else {
                        n2 = hitResult.p2;
                        n3 = -1;
                    }
                    shape5 = shape3;
                    shape3 = shape4;
                    shape4 = shape5;
                    continue;
                }
                if (shape3.contains(vector2f.x - f3, vector2f.y - f4)) {
                    if (bl) {
                        if (shape3 == shape) {
                            n2 = hitResult.p2;
                            n3 = -1;
                        } else {
                            n2 = hitResult.p1;
                            n3 = 1;
                        }
                    } else if (shape3 == shape2) {
                        n2 = hitResult.p1;
                        n3 = 1;
                    } else {
                        n2 = hitResult.p1;
                        n3 = 1;
                    }
                    shape5 = shape3;
                    shape3 = shape4;
                    shape4 = shape5;
                    continue;
                }
                if (bl) break;
                n2 = hitResult.p1;
                n3 = 1;
                shape5 = shape3;
                shape3 = shape4;
                shape4 = shape5;
                n2 = GeomUtil.rationalPoint(shape3, n2 + n3);
                f = shape3.getPoint(n2)[0];
                f2 = shape3.getPoint(n2)[1];
                continue;
            }
            n2 = GeomUtil.rationalPoint(shape3, n2 + n3);
            f = shape3.getPoint(n2)[0];
            f2 = shape3.getPoint(n2)[1];
        }
        polygon.addPoint(f, f2);
        if (this.listener != null) {
            this.listener.pointUsed(f, f2);
        }
        return polygon;
    }

    public HitResult intersect(Shape shape, Line line) {
        float f = Float.MAX_VALUE;
        HitResult hitResult = null;
        for (int i = 0; i < shape.getPointCount(); ++i) {
            float f2;
            int n = GeomUtil.rationalPoint(shape, i + 1);
            Line line2 = this.getLine(shape, i, n);
            Vector2f vector2f = line.intersect(line2, true);
            if (vector2f == null || !((f2 = vector2f.distance(line.getStart())) < f) || !(f2 > this.EPSILON)) continue;
            hitResult = new HitResult(this);
            hitResult.pt = vector2f;
            hitResult.line = line2;
            hitResult.p1 = i;
            hitResult.p2 = n;
            f = f2;
        }
        return hitResult;
    }

    public static int rationalPoint(Shape shape, int n) {
        while (n < 0) {
            n += shape.getPointCount();
        }
        while (n >= shape.getPointCount()) {
            n -= shape.getPointCount();
        }
        return n;
    }

    public Line getLine(Shape shape, int n, int n2) {
        float[] fArray = shape.getPoint(n);
        float[] fArray2 = shape.getPoint(n2);
        Line line = new Line(fArray[0], fArray[1], fArray2[0], fArray2[1]);
        return line;
    }

    public Line getLine(Shape shape, float f, float f2, int n) {
        float[] fArray = shape.getPoint(n);
        Line line = new Line(f, f2, fArray[0], fArray[1]);
        return line;
    }

    public class HitResult {
        public Line line;
        public int p1;
        public int p2;
        public Vector2f pt;
        final GeomUtil this$0;

        public HitResult(GeomUtil geomUtil) {
            this.this$0 = geomUtil;
        }
    }
}

