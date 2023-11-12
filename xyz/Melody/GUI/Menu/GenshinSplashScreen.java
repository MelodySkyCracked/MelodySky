/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.Drawable
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.SharedDrawable
 */
package xyz.Melody.GUI.Menu;

import java.awt.Color;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;
import xyz.Melody.Client;
import xyz.Melody.GUI.Animate.NonlinearOpacity;
import xyz.Melody.GUI.Animate.Opacity;
import xyz.Melody.GUI.Menu.I;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.render.Scissor;
import xyz.Melody.Utils.timer.TimerUtil;

public final class GenshinSplashScreen {
    private Opacity alpha = new Opacity(10);
    private NonlinearOpacity loadingX = new NonlinearOpacity(0);
    private TimerUtil timer = new TimerUtil();
    private TimerUtil delayTimer = new TimerUtil();
    private ScaledResolution scaledresolution;
    private ResourceLocation gens = new ResourceLocation("Melody/Genshin.png");
    private ResourceLocation gensl = new ResourceLocation("Melody/genshinLoad.png");
    private ResourceLocation gensldd = new ResourceLocation("Melody/genshinLoaded.png");
    private boolean alphaReached = false;
    private boolean genshinDone = false;
    private boolean mcLoaded = false;
    public static boolean paused;
    private static Drawable drawable;
    private static Thread thread;
    public static int progress;

    public void start(TextureManager textureManager, Minecraft minecraft, GenshinSplashScreen genshinSplashScreen) {
        try {
            drawable = new SharedDrawable(Display.getDrawable());
            Display.getDrawable().releaseContext();
            drawable.makeCurrent();
        }
        catch (LWJGLException lWJGLException) {
            lWJGLException.printStackTrace();
        }
        thread = new Thread(new I(this, genshinSplashScreen, textureManager, minecraft));
        thread.setName("Genshin Splash");
        thread.start();
    }

    public void drawScreen(TextureManager textureManager, Minecraft minecraft) {
        this.scaledresolution = new ScaledResolution(minecraft);
        Framebuffer framebuffer = this.pre();
        RenderUtil.drawFastRoundedRect(0.0f, 0.0f, this.getWidth(), this.getHeight(), 0.0f, ColorUtils.darker(Color.GRAY, 0.9f).getRGB());
        ResourceLocation resourceLocation = this.genshinDone ? this.gensl : this.gens;
        textureManager.func_110577_a(resourceLocation);
        int n = this.scaledresolution.func_78325_e();
        Scissor.start((this.getWidth() - 256) / 2 * n, (this.getHeight() - 256) / 2 * n, (this.getWidth() + 256) / 2 * n, (this.getHeight() + 256) / 2 * n);
        this.draw((this.getWidth() - 256) / 2, (this.getHeight() - 256) / 2, 512, 512, this.alpha.getOpacity());
        Scissor.end();
        if (this.genshinDone && this.alphaReached) {
            float f = progress;
            float f2 = 512.0f * f / 25.0f;
            this.loadingX.interp(f2, 3.0f);
            float f3 = this.loadingX.getOpacity();
            textureManager.func_110577_a(this.gensldd);
            Scissor.start((this.getWidth() - 256) / 2 * n, (this.getHeight() - 256) / 2 * n, ((float)(this.getWidth() - 256) + f3) / 2.0f * (float)n, (this.getHeight() + 256) / 2 * n);
            this.draw((this.getWidth() - 256) / 2, (this.getHeight() - 256) / 2, 512, 512, this.alpha.getOpacity());
            Scissor.end();
        } else {
            this.loadingX = new NonlinearOpacity(0);
        }
        if (!this.genshinDone) {
            if (this.alpha.getOpacity() == 10.0f) {
                this.alpha = new Opacity(15);
            }
            this.alpha.interp(this.alphaReached ? 10.0f : 255.0f, this.alphaReached ? 3.0f : 2.5f);
            if (this.alpha.getOpacity() >= 250.0f) {
                if (this.timer.hasReached(2000.0)) {
                    this.alphaReached = true;
                }
            } else {
                this.timer.reset();
            }
            if (this.alphaReached && this.alpha.getOpacity() <= 15.0f) {
                this.genshinDone = true;
                this.alphaReached = false;
                this.alpha = new Opacity(10);
                this.timer.reset();
            }
        } else if (this.timer.hasReached(500.0) && !this.alphaReached) {
            this.alpha.interp(255.0f, 4.0f);
            if (this.alpha.getOpacity() >= 250.0f) {
                this.alphaReached = true;
            }
        }
        this.post(framebuffer);
        if (!paused) {
            Display.update();
            Display.sync((int)90);
        }
    }

    private Framebuffer pre() {
        int n = this.scaledresolution.func_78325_e();
        Framebuffer framebuffer = new Framebuffer(this.scaledresolution.func_78326_a() * n, this.scaledresolution.func_78328_b() * n, true);
        framebuffer.func_147610_a(false);
        GlStateManager.func_179128_n((int)5889);
        GlStateManager.func_179096_D();
        GlStateManager.func_179130_a((double)0.0, (double)this.getWidth(), (double)this.getHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        GlStateManager.func_179128_n((int)5888);
        GlStateManager.func_179096_D();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)-2000.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179098_w();
        GlStateManager.func_179147_l();
        return framebuffer;
    }

    private void post(Framebuffer framebuffer) {
        try {
            int n = this.scaledresolution.func_78325_e();
            GlStateManager.func_179084_k();
            if (!this.getIsDrawing()) {
                framebuffer.func_147609_e();
                framebuffer.func_147615_c(this.scaledresolution.func_78326_a() * n, this.scaledresolution.func_78328_b() * n);
            }
            GlStateManager.func_179141_d();
            GlStateManager.func_179092_a((int)516, (float)0.1f);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private boolean getIsDrawing() {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        try {
            Class<?> clazz = worldRenderer.getClass();
            Field field = clazz.getDeclaredField("isDrawing");
            field.setAccessible(true);
            return field.getBoolean(worldRenderer);
        }
        catch (Exception exception) {
            try {
                Class<?> clazz = worldRenderer.getClass();
                Field field = clazz.getDeclaredField("field_179010_r");
                field.setAccessible(true);
                return field.getBoolean(worldRenderer);
            }
            catch (Exception exception2) {
                exception2.printStackTrace();
                return false;
            }
        }
    }

    public void draw(int n, int n2, int n3, int n4, float f) {
        int n5 = 255;
        int n6 = 255;
        int n7 = 255;
        int n8 = (int)f;
        int n9 = 0;
        int n10 = 0;
        float f2 = 0.00390625f;
        float f3 = 0.00390625f;
        try {
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
            WorldRenderer worldRenderer = Tessellator.func_178181_a().func_178180_c();
            if (!this.getIsDrawing()) {
                worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                worldRenderer.func_181662_b((double)n, (double)(n2 + n4), 0.0).func_181673_a((double)((float)n9 * f2), (double)((float)(n10 + n4) * f3)).func_181669_b(n5, n7, n6, n8).func_181675_d();
                worldRenderer.func_181662_b((double)(n + n3), (double)(n2 + n4), 0.0).func_181673_a((double)((float)(n9 + n3) * f2), (double)((float)(n10 + n4) * f3)).func_181669_b(n5, n7, n6, n8).func_181675_d();
                worldRenderer.func_181662_b((double)(n + n3), (double)n2, 0.0).func_181673_a((double)((float)(n9 + n3) * f2), (double)((float)n10 * f3)).func_181669_b(n5, n7, n6, n8).func_181675_d();
                worldRenderer.func_181662_b((double)n, (double)n2, 0.0).func_181673_a((double)((float)n9 * f2), (double)((float)n10 * f3)).func_181669_b(n5, n7, n6, n8).func_181675_d();
                Tessellator.func_178181_a().func_78381_a();
            }
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void finish() {
        try {
            this.mcLoaded = true;
            thread.join();
            drawable.releaseContext();
            Display.getDrawable().makeCurrent();
            Client.instance.logger.info("Finished Minecraft Loading.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private int getWidth() {
        return this.scaledresolution.func_78326_a();
    }

    private int getHeight() {
        return this.scaledresolution.func_78328_b();
    }

    static boolean access$000(GenshinSplashScreen genshinSplashScreen) {
        return genshinSplashScreen.mcLoaded;
    }

    static {
        progress = 0;
    }
}

