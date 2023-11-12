/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Triangulator;

public class NeatTriangulator
implements Triangulator {
    static final float EPSILON = 1.0E-6f;
    private float[] pointsX = new float[100];
    private float[] pointsY = new float[100];
    private int numPoints = 0;
    private Edge[] edges = new Edge[100];
    private int[] V;
    private int numEdges = 0;
    private Triangle[] triangles = new Triangle[100];
    private int numTriangles = 0;
    private float offset = 1.0E-6f;

    public void clear() {
        this.numPoints = 0;
        this.numEdges = 0;
        this.numTriangles = 0;
    }

    private int findEdge(int n, int n2) {
        int n3;
        int n4;
        if (n < n2) {
            n4 = n;
            n3 = n2;
        } else {
            n4 = n2;
            n3 = n;
        }
        for (int i = 0; i < this.numEdges; ++i) {
            if (this.edges[i].v0 != n4 || this.edges[i].v1 != n3) continue;
            return i;
        }
        return -1;
    }

    private void addEdge(int n, int n2, int n3) {
        int n4;
        int n5;
        Edge edge;
        int n6;
        int n7;
        int n8 = this.findEdge(n, n2);
        if (n8 < 0) {
            if (this.numEdges == this.edges.length) {
                Edge[] edgeArray = new Edge[this.edges.length * 2];
                System.arraycopy(this.edges, 0, edgeArray, 0, this.numEdges);
                this.edges = edgeArray;
            }
            n7 = -1;
            n6 = -1;
            n8 = this.numEdges++;
            edge = this.edges[n8] = new Edge(this);
        } else {
            edge = this.edges[n8];
            n7 = edge.t0;
            n6 = edge.t1;
        }
        if (n < n2) {
            n5 = n;
            n4 = n2;
            n7 = n3;
        } else {
            n5 = n2;
            n4 = n;
            n6 = n3;
        }
        edge.v0 = n5;
        edge.v1 = n4;
        edge.t0 = n7;
        edge.t1 = n6;
        edge.suspect = true;
    }

    private void deleteEdge(int n, int n2) throws InternalException {
        int n3 = this.findEdge(n, n2);
        if (0 > n3) {
            throw new InternalException(this, "Attempt to delete unknown edge");
        }
        this.edges[n3] = this.edges[--this.numEdges];
    }

    void markSuspect(int n, int n2, boolean bl) throws InternalException {
        int n3 = this.findEdge(n, n2);
        if (0 > n3) {
            throw new InternalException(this, "Attempt to mark unknown edge");
        }
        this.edges[n3].suspect = bl;
    }

    private Edge chooseSuspect() {
        for (int i = 0; i < this.numEdges; ++i) {
            Edge edge = this.edges[i];
            if (!edge.suspect) continue;
            edge.suspect = false;
            if (edge.t0 < 0 || edge.t1 < 0) continue;
            return edge;
        }
        return null;
    }

    private static float rho(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f5 - f3;
        float f8 = f2 - f6;
        float f9 = f6 - f4;
        float f10 = f - f5;
        float f11 = f7 * f8 - f9 * f10;
        if (f11 > 0.0f) {
            if (f11 < 1.0E-6f) {
                f11 = 1.0E-6f;
            }
            float f12 = f7 * f7;
            float f13 = f9 * f9;
            float f14 = f10 * f10;
            float f15 = f8 * f8;
            float f16 = f3 - f;
            float f17 = f4 - f2;
            float f18 = f16 * f16;
            float f19 = f17 * f17;
            return (f12 + f13) * (f14 + f15) * (f18 + f19) / (f11 * f11);
        }
        return -1.0f;
    }

    private float area() {
        float f = 0.0f;
        int n = this.numPoints - 1;
        int n2 = 0;
        while (n2 < this.numPoints) {
            f += this.pointsX[n] * this.pointsY[n2] - this.pointsY[n] * this.pointsX[n2];
            n = n2++;
        }
        return f * 0.5f;
    }

    /*
     * Exception decompiling
     */
    public void basicTriangulation() throws InternalException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl62 : ILOAD_1 - null : trying to set 4 previously set to 0
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

    private void optimize() throws InternalException {
        Edge edge = this.chooseSuspect();
        if (edge != null) {
            int n;
            int n2;
            int n3 = edge.v0;
            int n4 = edge.v1;
            int n5 = edge.t0;
            int n6 = edge.t1;
            int n7 = -1;
            int n8 = -1;
            for (n2 = 0; n2 < 3; ++n2) {
                n = this.triangles[n5].v[n2];
                if (n3 == n || n4 == n) continue;
                n8 = n;
                break;
            }
            for (n2 = 0; n2 < 3; ++n2) {
                n = this.triangles[n6].v[n2];
                if (n3 == n || n4 == n) continue;
                n7 = n;
                break;
            }
            if (-1 == n7 || -1 == n8) {
                throw new InternalException(this, "can't find quad");
            }
            float f = this.pointsX[n3];
            float f2 = this.pointsY[n3];
            float f3 = this.pointsX[n7];
            float f4 = this.pointsY[n7];
            float f5 = this.pointsX[n4];
            float f6 = this.pointsY[n4];
            float f7 = this.pointsX[n8];
            float f8 = this.pointsY[n8];
            float f9 = NeatTriangulator.rho(f, f2, f3, f4, f5, f6);
            float f10 = NeatTriangulator.rho(f, f2, f5, f6, f7, f8);
            float f11 = NeatTriangulator.rho(f3, f4, f5, f6, f7, f8);
            float f12 = NeatTriangulator.rho(f3, f4, f7, f8, f, f2);
            if (0.0f > f9 || 0.0f > f10) {
                throw new InternalException(this, "original triangles backwards");
            }
            if (0.0f <= f11 && 0.0f <= f12) {
                if (f9 > f10) {
                    f9 = f10;
                }
                if (f11 > f12) {
                    f11 = f12;
                }
                if (f9 > f11) {
                    this.deleteEdge(n3, n4);
                    this.triangles[n5].v[0] = n7;
                    this.triangles[n5].v[1] = n4;
                    this.triangles[n5].v[2] = n8;
                    this.triangles[n6].v[0] = n7;
                    this.triangles[n6].v[1] = n8;
                    this.triangles[n6].v[2] = n3;
                    this.addEdge(n7, n4, n5);
                    this.addEdge(n4, n8, n5);
                    this.addEdge(n8, n7, n5);
                    this.addEdge(n8, n3, n6);
                    this.addEdge(n3, n7, n6);
                    this.addEdge(n7, n8, n6);
                    this.markSuspect(n7, n8, false);
                }
            }
        }
    }

    @Override
    public boolean triangulate() {
        try {
            this.basicTriangulation();
            return true;
        }
        catch (InternalException internalException) {
            this.numEdges = 0;
            return false;
        }
    }

    @Override
    public void addPolyPoint(float f, float f2) {
        for (int i = 0; i < this.numPoints; ++i) {
            if (this.pointsX[i] != f || this.pointsY[i] != f2) continue;
            f2 += this.offset;
            this.offset += 1.0E-6f;
        }
        if (this.numPoints == this.pointsX.length) {
            float[] fArray = new float[this.numPoints * 2];
            System.arraycopy(this.pointsX, 0, fArray, 0, this.numPoints);
            this.pointsX = fArray;
            fArray = new float[this.numPoints * 2];
            System.arraycopy(this.pointsY, 0, fArray, 0, this.numPoints);
            this.pointsY = fArray;
        }
        this.pointsX[this.numPoints] = f;
        this.pointsY[this.numPoints] = f2;
        ++this.numPoints;
    }

    @Override
    public int getTriangleCount() {
        return this.numTriangles;
    }

    @Override
    public float[] getTrianglePoint(int n, int n2) {
        float f = this.pointsX[this.triangles[n].v[n2]];
        float f2 = this.pointsY[this.triangles[n].v[n2]];
        return new float[]{f, f2};
    }

    @Override
    public void startHole() {
    }

    class InternalException
    extends Exception {
        final NeatTriangulator this$0;

        public InternalException(NeatTriangulator neatTriangulator, String string) {
            this.this$0 = neatTriangulator;
            super(string);
        }
    }

    class Edge {
        int v0;
        int v1;
        int t0;
        int t1;
        boolean suspect;
        final NeatTriangulator this$0;

        Edge(NeatTriangulator neatTriangulator) {
            this.this$0 = neatTriangulator;
            this.v0 = -1;
            this.v1 = -1;
            this.t0 = -1;
            this.t1 = -1;
        }
    }

    class Triangle {
        int[] v;
        final NeatTriangulator this$0;

        Triangle(NeatTriangulator neatTriangulator, int n, int n2, int n3) {
            this.this$0 = neatTriangulator;
            this.v = new int[3];
            this.v[0] = n;
            this.v[1] = n2;
            this.v[2] = n3;
        }
    }
}

