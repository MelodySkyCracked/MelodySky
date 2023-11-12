/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.Particles.Physic;

import java.util.ArrayList;
import xyz.Melody.GUI.Particles.Physic.HUHUtils;

public final class MenuParticle {
    public double x;
    public double y;
    public float density;
    public double motionX;
    public double motionY;
    public float mass = 1.0f;
    public float gravityAcceleration = 0.2f;
    public float[] pressureForce = new float[]{0.0f, 0.0f};
    public float[] viscosityForce = new float[]{0.0f, 0.0f};

    public MenuParticle(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public void update(int n, int n2, ArrayList arrayList) {
        HUHUtils.updateDensity(this, arrayList);
        this.motionX += (double)(this.pressureForce[0] / this.density);
        this.motionY += (double)(this.pressureForce[1] / this.density);
        this.motionX += (double)(this.viscosityForce[0] / this.density);
        this.motionY += (double)(this.viscosityForce[1] / this.density);
        float f = 0.765f;
        this.motionY *= (double)0.985f;
        this.motionX *= (double)0.985f;
        this.x += this.motionX;
        this.y += this.motionY;
        if (this.x < 0.0) {
            this.x = 0.0;
            this.motionX *= (double)(-f);
        } else if (this.x > (double)n) {
            this.x = n;
            this.motionX *= (double)(-f);
        }
        if (this.y < 0.0) {
            this.y = 0.0;
            this.motionY *= (double)(-f);
        } else if (this.y > (double)n2) {
            this.y = n2;
            this.motionY *= (double)(-f);
        }
    }

    public MenuParticle addMotion(double d, double d2) {
        this.motionX += d;
        this.motionY += d2;
        return this;
    }
}

