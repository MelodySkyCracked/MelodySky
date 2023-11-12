/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.GUI.Particles.Physic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import xyz.Melody.GUI.Particles.Physic.HUHUtils;
import xyz.Melody.GUI.Particles.Physic.MenuParticle;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;

public class PhysicalParticle {
    public static PhysicalParticle phycle;
    private int width;
    private int height;
    private ArrayList particles = new ArrayList();
    private Random rand = new Random();
    private int particleCount = 1000;
    private Thread calculationThread;
    public boolean paused = false;
    private float roundRadius = 2.0f;

    public static void init() {
        phycle = new PhysicalParticle();
    }

    public void reset(int n, int n2) {
        this.paused = false;
        this.particles.clear();
        this.width = n;
        this.height = n2;
        this.roundRadius = 3.0f;
        HUHUtils.smoothingRad = 5.0f;
        HUHUtils.pressureMultiper = 0.5f;
        HUHUtils.targetDensity = 1.5f;
        HUHUtils.viscosityMultiper = 0.3f;
        this.particleCount = n * n2 / 256;
        double d = 0.0 + (double)(n - 0) * this.rand.nextDouble();
        double d2 = 0.0 + (double)(n2 - 0) * this.rand.nextDouble();
        for (int i = 0; i < this.particleCount; ++i) {
            MenuParticle menuParticle = new MenuParticle(d, d2);
            this.particles.add(menuParticle);
        }
        PhysicalParticle physicalParticle = this;
        int n3 = n;
        if (n2 != null) {
            this.calculationThread.start();
        }
    }

    public void render() {
        if (!this.particles.isEmpty()) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_179090_x();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            for (MenuParticle menuParticle : this.particles) {
                GlStateManager.func_179131_c((float)0.5f, (float)0.6f, (float)1.0f, (float)0.15f);
                float f = (float)menuParticle.x;
                float f2 = (float)menuParticle.y;
                float[] fArray = new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f};
                Color[] colorArray = new Color[]{ColorUtils.darker(new Color(Colors.BLUE.c), 0.3f), ColorUtils.darker(new Color(Colors.GREEN.c), 0.4f), ColorUtils.darker(new Color(Colors.YELLOW.c), 0.4f), ColorUtils.darker(new Color(Colors.ORANGE.c), 0.4f), new Color(200, 0, 0)};
                float f3 = (float)(Math.sqrt(menuParticle.motionX * menuParticle.motionX + menuParticle.motionY * menuParticle.motionY) / 15.0);
                Color color = ColorUtils.blendColors(fArray, colorArray, f3).brighter();
                RenderUtil.glColor(ColorUtils.addAlpha(color, 120));
                double d = 0.7853981633974483;
                GL11.glBegin((int)6);
                for (int i = 0; i < 8; ++i) {
                    float f4 = (float)((double)this.roundRadius * Math.sin((double)i * d));
                    float f5 = (float)((double)this.roundRadius * Math.cos((double)i * d));
                    GL11.glVertex2f((float)(f + f4), (float)(f2 + f5));
                }
                GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
                GL11.glEnd();
            }
            GlStateManager.func_179121_F();
        }
    }

    public void onMouseClicked(int n, int n2, int n3) {
        for (MenuParticle menuParticle : this.particles) {
            float f = (float)Math.toDegrees(Math.atan2((double)n2 - menuParticle.y, (double)n - menuParticle.x));
            if (f < 0.0f) {
                f += 360.0f;
            }
            double d = (double)n - menuParticle.x;
            double d2 = (double)n2 - menuParticle.y;
            double d3 = Math.sqrt(d * d + d2 * d2);
            double d4 = Math.cos(Math.toRadians(f));
            double d5 = Math.sin(Math.toRadians(f));
            if (d3 < 20.0) {
                d3 = 20.0;
            }
            if (n3 == 0 && d3 <= 100.0) {
                menuParticle.motionX -= d4 * 400.0 / (d3 * 3.0);
                menuParticle.motionY -= d5 * 400.0 / (d3 * 3.0);
                continue;
            }
            if (!(d3 <= 250.0)) continue;
            menuParticle.motionX += d4 / 5.0 / d3 * 200.0;
            menuParticle.motionY += d5 / 5.0 / d3 * 200.0;
        }
    }

    private void lambda$checkThreadState$0() {
        while (!this.particles.isEmpty() && !this.paused) {
            if (Minecraft.func_71410_x().field_71462_r == null) {
                this.paused = true;
            }
            try {
                long l2 = System.currentTimeMillis();
                for (int i = 0; i < this.particleCount; ++i) {
                    MenuParticle menuParticle = (MenuParticle)this.particles.get(i);
                    menuParticle.pressureForce = HUHUtils.calculatePressureForce(i, this.particles);
                    menuParticle.viscosityForce = HUHUtils.calculateViscosityForce(i, this.particles);
                    menuParticle.update(this.width, this.height, this.particles);
                }
                long l3 = 33L - (System.currentTimeMillis() - l2);
                if (l3 <= 0L) continue;
                Thread.sleep(l3);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.calculationThread = null;
    }
}

