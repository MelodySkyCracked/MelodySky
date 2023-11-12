/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.GUI.Particles.Physic;

import java.util.ArrayList;
import java.util.Random;
import xyz.Melody.GUI.Particles.Physic.MenuParticle;

public class HUHUtils {
    public static float smoothingRad = 10.0f;
    public static float targetDensity = 2.75f;
    public static float pressureMultiper = 0.5f;
    public static float viscosityMultiper = 0.5f;
    public static float nearPressureMultiper = 0.5f;

    public static float[] density2Pressure(float f, float f2) {
        float f3 = f - targetDensity;
        float f4 = f3 * pressureMultiper;
        float f5 = f2 * nearPressureMultiper;
        return new float[]{f4, f5};
    }

    public static void updateDensity(MenuParticle menuParticle, ArrayList arrayList) {
        menuParticle.density = HUHUtils.calculateDensity(menuParticle, arrayList);
    }

    public static float calculateDensity(MenuParticle menuParticle, ArrayList arrayList) {
        float f = 0.0f;
        float f2 = 1.0f;
        double d = menuParticle.x;
        double d2 = menuParticle.y;
        for (MenuParticle menuParticle2 : arrayList) {
            double d3 = menuParticle2.x - d;
            double d4 = menuParticle2.y - d2;
            float f3 = (float)Math.sqrt(d3 * d3 + d4 * d4);
            float f4 = HUHUtils.smoothingKernel(smoothingRad, f3);
            f += f4 * f2;
        }
        return f;
    }

    public static float[] calculateViscosityForce(int n, ArrayList arrayList) {
        MenuParticle menuParticle = (MenuParticle)arrayList.get(n);
        float[] fArray = new float[]{0.0f, 0.0f};
        for (int i = 0; i < arrayList.size(); ++i) {
            if (i == n) continue;
            MenuParticle menuParticle2 = (MenuParticle)arrayList.get(i);
            float f = (float)(menuParticle.x - menuParticle2.x);
            float f2 = (float)(menuParticle.y - menuParticle2.y);
            float f3 = (float)Math.sqrt(f * f + f2 * f2);
            if (f3 > 20.0f) continue;
            float f4 = HUHUtils.smoothingKernelVis(f3, smoothingRad);
            fArray[0] = (float)((double)fArray[0] + (menuParticle2.motionX - menuParticle.motionX) * (double)f4);
            fArray[1] = (float)((double)fArray[1] + (menuParticle2.motionY - menuParticle.motionY) * (double)f4);
        }
        fArray[0] = fArray[0] * (viscosityMultiper / 1.0E9f);
        fArray[1] = fArray[1] * (viscosityMultiper / 1.0E9f);
        return fArray;
    }

    public static float[] calculatePressureForce(int n, ArrayList arrayList) {
        MenuParticle menuParticle = (MenuParticle)arrayList.get(n);
        float[] fArray = new float[]{0.0f, 0.0f};
        float f = 1.0f;
        for (int i = 0; i < arrayList.size(); ++i) {
            float f2;
            float f3;
            float f4;
            float f5;
            MenuParticle menuParticle2;
            if (i == n || (menuParticle2 = (MenuParticle)arrayList.get(i)) == null || (f5 = (float)Math.sqrt((f4 = (float)(menuParticle2.x - menuParticle.x)) * f4 + (f3 = (float)(menuParticle2.y - menuParticle.y)) * f3)) > smoothingRad * 10.0f) continue;
            float f6 = new Random().nextFloat();
            float f7 = new Random().nextFloat();
            float f8 = f5 <= 4.0f ? new Random().nextFloat() * (float)(f6 > 0.5f ? -1 : 1) : (f2 = f4 / f5);
            float f9 = f5 <= 4.0f ? new Random().nextFloat() * (float)(f7 > 0.5f ? -1 : 1) : f3 / f5;
            float f10 = HUHUtils.smoothingKernelDerivative(f5, smoothingRad);
            float f11 = menuParticle2.density;
            if (f11 == 0.0f) continue;
            float f12 = HUHUtils.calculateSharedPressure(f11, menuParticle.density);
            fArray[0] = fArray[0] + -f12 * f2 * f10 * f / f11;
            fArray[1] = fArray[1] + -f12 * f9 * f10 * f / f11;
        }
        return fArray;
    }

    public static float calculateSharedPressure(float f, float f2) {
        float f3 = HUHUtils.density2Pressure(f, targetDensity)[0];
        float f4 = HUHUtils.density2Pressure(f2, targetDensity)[0];
        return (f3 + f4) / 2.0f;
    }

    public static float smoothingKernel(float f, float f2) {
        if (f2 >= f) {
            return 0.0f;
        }
        float f3 = (float)(Math.PI * Math.pow(f, 4.0) / 6.0);
        return (f - f2) * (f - f2) / f3;
    }

    public static float smoothingKernelDerivative(float f, float f2) {
        if (f2 >= f) {
            return 0.0f;
        }
        float f3 = (float)(12.0 / (Math.PI * Math.pow(f, 4.0)));
        return (f2 - f) * f3;
    }

    public static float smoothingKernelVis(float f, float f2) {
        float f3 = Math.max(0.0f, f * f - f2 * f2);
        return f3 * f3 * f3;
    }
}

