/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.Animate;

public final class NonlinearOpacity {
    private float lastTarget = 0.0f;
    private float sumMovement = 0.0f;
    private float opacity;
    private long lastMS;

    public NonlinearOpacity(int n) {
        this.opacity = n;
        this.lastMS = System.currentTimeMillis();
    }

    public void interpolate(float f) {
        this.interp(f, 20.0f);
    }

    public void interp(float f, float f2) {
        long l2 = System.currentTimeMillis();
        long l3 = l2 - this.lastMS;
        if (l3 > 10L) {
            l3 = 10L;
        } else if (l3 < 10L) {
            return;
        }
        this.opacity = this.calculateCompensation(f, this.opacity, l3, f2);
        this.lastMS = l2;
    }

    public float calculateCompensation(float f, float f2, long l2, float f3) {
        double d;
        if (Math.abs(f - f2) == 0.0f) {
            this.sumMovement = 0.0f;
            return f2;
        }
        if (this.lastTarget != f) {
            this.lastTarget = f;
            this.sumMovement = 0.0f;
        }
        float f4 = f2 - this.sumMovement;
        float f5 = f - f4;
        float f6 = f2;
        float f7 = f2 - f;
        if (l2 < 1L) {
            l2 = 1L;
        }
        double d2 = d = (double)((f3 = Math.abs((f3 / 2.0f - f3 * (f7 / f5) * 3.0f * 2.5f) * 0.4f) * 0.7f) * (float)l2 / 16.0f) < 0.25 ? 0.5 : (double)(f3 * (float)l2 / 16.0f);
        if (!(f7 > f3) && !(f7 < -f3)) {
            f3 /= 10.0f;
        }
        if (f7 > f3) {
            if ((f2 = (float)((double)f2 - d)) < f) {
                f2 = f;
            }
        } else if (f7 < -f3) {
            if ((f2 = (float)((double)f2 + d)) > f) {
                f2 = f;
            }
        } else {
            f2 = f;
        }
        this.sumMovement += f2 - f6;
        return f2;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }
}

