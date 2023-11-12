/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.EXTFramebufferObject
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Event.events.rendering.EventRenderEntityModel;

public class OutlineUtils {
    private static Minecraft mc = Minecraft.func_71410_x();

    public static void outlineEntity(ModelBase modelBase, Entity entity, float f, float f2, float f3, float f4, float f5, float f6, Color color, int n) {
        boolean bl = OutlineUtils.mc.field_71474_y.field_74347_j;
        float f7 = OutlineUtils.mc.field_71474_y.field_74333_Y;
        OutlineUtils.mc.field_71474_y.field_74347_j = false;
        OutlineUtils.mc.field_71474_y.field_74333_Y = Float.MAX_VALUE;
        OutlineUtils.enableESP();
        GlStateManager.func_179117_G();
        OutlineUtils.setColor(color);
        OutlineUtils.renderOne(n);
        modelBase.func_78088_a(entity, f, f2, f3, f4, f5, f6);
        OutlineUtils.setColor(color);
        OutlineUtils.renderTwo();
        modelBase.func_78088_a(entity, f, f2, f3, f4, f5, f6);
        OutlineUtils.setColor(color);
        OutlineUtils.renderThree();
        modelBase.func_78088_a(entity, f, f2, f3, f4, f5, f6);
        OutlineUtils.setColor(color);
        OutlineUtils.renderFour(color);
        modelBase.func_78088_a(entity, f, f2, f3, f4, f5, f6);
        OutlineUtils.setColor(color);
        OutlineUtils.renderFive();
        OutlineUtils.setColor(Color.WHITE);
        OutlineUtils.disableESP();
        OutlineUtils.mc.field_71474_y.field_74347_j = bl;
        OutlineUtils.mc.field_71474_y.field_74333_Y = f7;
    }

    public static void enableESP() {
        GlStateManager.func_179129_p();
        GlStateManager.func_179097_i();
    }

    public static void disableESP() {
        GlStateManager.func_179089_o();
        GlStateManager.func_179126_j();
    }

    public static void outlineEntity(EventRenderEntityModel eventRenderEntityModel, Color color, int n) {
        OutlineUtils.outlineEntity(eventRenderEntityModel.getModel(), (Entity)eventRenderEntityModel.getEntity(), eventRenderEntityModel.getLimbSwing(), eventRenderEntityModel.getLimbSwingAmount(), eventRenderEntityModel.getAgeInTicks(), eventRenderEntityModel.getHeadYaw(), eventRenderEntityModel.getHeadPitch(), eventRenderEntityModel.getScaleFactor(), color, n);
    }

    private static void renderOne(float f) {
        OutlineUtils.checkSetupFBO();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    private static void renderTwo() {
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6914);
    }

    private static void renderThree() {
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    private static void renderFour(Color color) {
        OutlineUtils.setColor(color);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)10754);
        GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
        OpenGlHelper.func_77475_a((int)OpenGlHelper.field_77476_b, (float)240.0f, (float)240.0f);
    }

    private static void renderFive() {
        GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
        GL11.glDisable((int)10754);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2960);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
    }

    private static void setColor(Color color) {
        GL11.glColor4d((double)((float)color.getRed() / 255.0f), (double)((float)color.getGreen() / 255.0f), (double)((float)color.getBlue() / 255.0f), (double)((float)color.getAlpha() / 255.0f));
    }

    private static void checkSetupFBO() {
        Framebuffer framebuffer = mc.func_147110_a();
        if (framebuffer != null && framebuffer.field_147624_h > -1) {
            OutlineUtils.setupFBO(framebuffer);
            framebuffer.field_147624_h = -1;
        }
    }

    private static void setupFBO(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)framebuffer.field_147624_h);
        int n = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)n);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)OutlineUtils.mc.field_71443_c, (int)OutlineUtils.mc.field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)n);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)n);
    }
}

