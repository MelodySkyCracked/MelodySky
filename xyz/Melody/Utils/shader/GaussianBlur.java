/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL20
 */
package xyz.Melody.Utils.shader;

import java.awt.Color;
import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.render.Stencil;
import xyz.Melody.Utils.render.gl.GLUtils;
import xyz.Melody.Utils.shader.ShaderUtils;
import xyz.Melody.module.modules.render.HUD;

public final class GaussianBlur {
    private ShaderUtils blurShader = new ShaderUtils("Melody/GLSL/Shaders/gaussian.frag");
    private Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private Framebuffer framebufferRound = new Framebuffer(1, 1, false);
    private Minecraft mc = Minecraft.func_71410_x();

    private void setupUniforms(float f, float f2, float f3, float f4) {
        this.blurShader.setUniformi("textureIn", 0);
        this.blurShader.setUniformf("texelSize", 1.0f / (float)this.mc.field_71443_c, 1.0f / (float)this.mc.field_71440_d);
        this.blurShader.setUniformf("direction", f, f2);
        this.blurShader.setUniformf("radius", f3);
        this.blurShader.setUniformf("quality", f4);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer((int)256);
        int n = 0;
        while ((float)n <= f3) {
            floatBuffer.put(MathUtil.calculateGaussianValue(n, f3 / 2.0f));
            ++n;
        }
        floatBuffer.rewind();
        GL20.glUniform1((int)this.blurShader.getUniform("weights"), (FloatBuffer)floatBuffer);
    }

    public void blurArea(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        Stencil.initStencil();
        Stencil.bindWriteStencilBuffer();
        RenderUtil.drawFastRoundedRect(f, f2, f3, f4, n, n2);
        Stencil.bindReadStencilBuffer(1);
        this.renderBlur(n3);
        Stencil.uninitStencil();
    }

    public void blurArea(float f, float f2, float f3, float f4, int n) {
        this.blurArea(f, f2, f3, f4, 0, Color.white.getRGB(), n);
    }

    public void renderBlur(float f) {
        float f2;
        if (f <= 0.0f) {
            return;
        }
        float f3 = ((Double)HUD.getInstance().blurQuality.getValue()).floatValue();
        if (!((Boolean)HUD.getInstance().qualityBoom.getValue()).booleanValue()) {
            if (f3 < 64.0f) {
                f2 = f / f3;
                if (f2 < 1.0f) {
                    f2 = 1.0f;
                }
            } else {
                f2 = 1.0f;
            }
        } else {
            f2 = (float)Math.pow(10.0, -4.0);
        }
        this.renderBlur(f, 1, 0, f2);
        this.renderBlur(f, 0, 1, f2);
    }

    private void renderBlur(float f, int n, int n2, float f2) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        this.framebuffer = GLUtils.createFrameBuffer(this.framebuffer);
        this.framebuffer.func_147614_f();
        this.framebuffer.func_147610_a(true);
        this.blurShader.init();
        this.setupUniforms(n, n2, f, f2);
        GLUtils.bindTexture(this.mc.func_147110_a().field_147617_g);
        ShaderUtils.drawQuads();
        this.framebuffer.func_147609_e();
        this.blurShader.unload();
        this.mc.func_147110_a().func_147610_a(true);
        GLUtils.bindTexture(this.framebuffer.field_147617_g);
        ShaderUtils.drawQuads();
        GlStateManager.func_179117_G();
        GlStateManager.func_179144_i((int)0);
        GlStateManager.func_179121_F();
    }
}

