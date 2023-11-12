/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Transform {
    private float[] matrixPosition;

    public Transform() {
        this.matrixPosition = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Transform(Transform transform) {
        this.matrixPosition = new float[9];
        for (int i = 0; i < 9; ++i) {
            this.matrixPosition[i] = transform.matrixPosition[i];
        }
    }

    public Transform(Transform transform, Transform transform2) {
        this(transform);
        this.concatenate(transform2);
    }

    public Transform(float[] fArray) {
        if (fArray.length != 6) {
            throw new RuntimeException("The parameter must be a float array of length 6.");
        }
        this.matrixPosition = new float[]{fArray[0], fArray[1], fArray[2], fArray[3], fArray[4], fArray[5], 0.0f, 0.0f, 1.0f};
    }

    public Transform(float f, float f2, float f3, float f4, float f5, float f6) {
        this.matrixPosition = new float[]{f, f2, f3, f4, f5, f6, 0.0f, 0.0f, 1.0f};
    }

    public void transform(float[] fArray, int n, float[] fArray2, int n2, int n3) {
        int n4;
        float[] fArray3 = fArray == fArray2 ? new float[n3 * 2] : fArray2;
        for (n4 = 0; n4 < n3 * 2; n4 += 2) {
            for (int i = 0; i < 6; i += 3) {
                fArray3[n4 + i / 3] = fArray[n4 + n] * this.matrixPosition[i] + fArray[n4 + n + 1] * this.matrixPosition[i + 1] + 1.0f * this.matrixPosition[i + 2];
            }
        }
        if (fArray == fArray2) {
            for (n4 = 0; n4 < n3 * 2; n4 += 2) {
                fArray2[n4 + n2] = fArray3[n4];
                fArray2[n4 + n2 + 1] = fArray3[n4 + 1];
            }
        }
    }

    public Transform concatenate(Transform transform) {
        float[] fArray = new float[9];
        float f = this.matrixPosition[0] * transform.matrixPosition[0] + this.matrixPosition[1] * transform.matrixPosition[3];
        float f2 = this.matrixPosition[0] * transform.matrixPosition[1] + this.matrixPosition[1] * transform.matrixPosition[4];
        float f3 = this.matrixPosition[0] * transform.matrixPosition[2] + this.matrixPosition[1] * transform.matrixPosition[5] + this.matrixPosition[2];
        float f4 = this.matrixPosition[3] * transform.matrixPosition[0] + this.matrixPosition[4] * transform.matrixPosition[3];
        float f5 = this.matrixPosition[3] * transform.matrixPosition[1] + this.matrixPosition[4] * transform.matrixPosition[4];
        float f6 = this.matrixPosition[3] * transform.matrixPosition[2] + this.matrixPosition[4] * transform.matrixPosition[5] + this.matrixPosition[5];
        fArray[0] = f;
        fArray[1] = f2;
        fArray[2] = f3;
        fArray[3] = f4;
        fArray[4] = f5;
        fArray[5] = f6;
        this.matrixPosition = fArray;
        return this;
    }

    public String toString() {
        String string = "Transform[[" + this.matrixPosition[0] + "," + this.matrixPosition[1] + "," + this.matrixPosition[2] + "][" + this.matrixPosition[3] + "," + this.matrixPosition[4] + "," + this.matrixPosition[5] + "][" + this.matrixPosition[6] + "," + this.matrixPosition[7] + "," + this.matrixPosition[8] + "]]";
        return string.toString();
    }

    public float[] getMatrixPosition() {
        return this.matrixPosition;
    }

    public static Transform createRotateTransform(float f) {
        return new Transform((float)FastTrig.cos(f), -((float)FastTrig.sin(f)), 0.0f, (float)FastTrig.sin(f), (float)FastTrig.cos(f), 0.0f);
    }

    public static Transform createRotateTransform(float f, float f2, float f3) {
        Transform transform = Transform.createRotateTransform(f);
        float f4 = transform.matrixPosition[3];
        float f5 = 1.0f - transform.matrixPosition[4];
        transform.matrixPosition[2] = f2 * f5 + f3 * f4;
        transform.matrixPosition[5] = f3 * f5 - f2 * f4;
        return transform;
    }

    public static Transform createTranslateTransform(float f, float f2) {
        return new Transform(1.0f, 0.0f, f, 0.0f, 1.0f, f2);
    }

    public static Transform createScaleTransform(float f, float f2) {
        return new Transform(f, 0.0f, 0.0f, 0.0f, f2, 0.0f);
    }

    public Vector2f transform(Vector2f vector2f) {
        float[] fArray = new float[]{vector2f.x, vector2f.y};
        float[] fArray2 = new float[2];
        this.transform(fArray, 0, fArray2, 0, 1);
        return new Vector2f(fArray2[0], fArray2[1]);
    }
}

