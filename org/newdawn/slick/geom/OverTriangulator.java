/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Triangulator;

public class OverTriangulator
implements Triangulator {
    private float[][] triangles;

    public OverTriangulator(Triangulator triangulator) {
        this.triangles = new float[triangulator.getTriangleCount() * 6 * 3][2];
        int n = 0;
        for (int i = 0; i < triangulator.getTriangleCount(); ++i) {
            float[] fArray;
            float[] fArray2;
            int n2;
            float f = 0.0f;
            float f2 = 0.0f;
            for (n2 = 0; n2 < 3; ++n2) {
                float[] fArray3 = triangulator.getTrianglePoint(i, n2);
                f += fArray3[0];
                f2 += fArray3[1];
            }
            f /= 3.0f;
            f2 /= 3.0f;
            for (n2 = 0; n2 < 3; ++n2) {
                int n3 = n2 + 1;
                if (n3 > 2) {
                    n3 = 0;
                }
                fArray2 = triangulator.getTrianglePoint(i, n2);
                fArray = triangulator.getTrianglePoint(i, n3);
                fArray2[0] = (fArray2[0] + fArray[0]) / 2.0f;
                fArray2[1] = (fArray2[1] + fArray[1]) / 2.0f;
                this.triangles[n * 3 + 0][0] = f;
                this.triangles[n * 3 + 0][1] = f2;
                this.triangles[n * 3 + 1][0] = fArray2[0];
                this.triangles[n * 3 + 1][1] = fArray2[1];
                this.triangles[n * 3 + 2][0] = fArray[0];
                this.triangles[n * 3 + 2][1] = fArray[1];
                ++n;
            }
            for (n2 = 0; n2 < 3; ++n2) {
                int n4 = n2 + 1;
                if (n4 > 2) {
                    n4 = 0;
                }
                fArray2 = triangulator.getTrianglePoint(i, n2);
                fArray = triangulator.getTrianglePoint(i, n4);
                fArray[0] = (fArray2[0] + fArray[0]) / 2.0f;
                fArray[1] = (fArray2[1] + fArray[1]) / 2.0f;
                this.triangles[n * 3 + 0][0] = f;
                this.triangles[n * 3 + 0][1] = f2;
                this.triangles[n * 3 + 1][0] = fArray2[0];
                this.triangles[n * 3 + 1][1] = fArray2[1];
                this.triangles[n * 3 + 2][0] = fArray[0];
                this.triangles[n * 3 + 2][1] = fArray[1];
                ++n;
            }
        }
    }

    @Override
    public void addPolyPoint(float f, float f2) {
    }

    @Override
    public int getTriangleCount() {
        return this.triangles.length / 3;
    }

    @Override
    public float[] getTrianglePoint(int n, int n2) {
        float[] fArray = this.triangles[n * 3 + n2];
        return new float[]{fArray[0], fArray[1]};
    }

    @Override
    public void startHole() {
    }

    @Override
    public boolean triangulate() {
        return true;
    }
}

