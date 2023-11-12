/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EnumPlayerModelParts
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.Utils.render;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;
import pw.knx.feather.tessellate.Tessellation;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.System.Managers.Skyblock.JiaRan.RenderTypes.Beacon;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.math.Vec2f;
import xyz.Melody.Utils.math.Vec3f;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.gl.GLClientState;
import xyz.Melody.Utils.render.gl.GLUtil;
import xyz.Melody.module.modules.render.Cam;

public final class RenderUtil {
    public static final Tessellation tessellator;
    private static final List csBuffer;
    private static final Consumer ENABLE_CLIENT_STATE;
    private static final Consumer DISABLE_CLIENT_STATE;
    private static Minecraft mc;
    public static BufferedReader reader;
    public static Socket s;
    public static PrintWriter w;
    public static InputStream i;

    public static void drawImage(ResourceLocation resourceLocation, float f, float f2, float f3, float f4) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(resourceLocation);
        Gui.func_146110_a((int)((int)f), (int)((int)f2), (float)0.0f, (float)0.0f, (int)((int)f3), (int)((int)f4), (float)f3, (float)f4);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawBorderedRect(float f, float f2, float f3, float f4, float f5, int n) {
        RenderUtil.outlineRect(f, f2, f3, f4, n, 0.0f, f5);
    }

    public static void outlineRect(float f, float f2, float f3, float f4, int n, float f5, float f6) {
        int n2;
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        f *= 2.0f;
        f2 *= 2.0f;
        f3 = (float)((double)f3 * 2.0);
        f4 = (float)((double)f4 * 2.0);
        GL11.glLineWidth((float)f6);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        RenderUtil.glColor(n);
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d((double)((double)(f + f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)), (double)((double)(f2 + f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)));
        }
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d((double)((double)(f + f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)), (double)((double)(f4 - f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)));
        }
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d((double)((double)(f3 - f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)f5), (double)((double)(f4 - f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)f5));
        }
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d((double)((double)(f3 - f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)f5), (double)((double)(f2 + f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)f5));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void outlineRainbow(float f, float f2, float f3, float f4, float f5, float f6) {
        RenderUtil.outlineRainbow(f, f2, f3, f4, f5, f6, 255);
    }

    public static void outlineRainbow(float f, float f2, float f3, float f4, float f5, float f6, int n) {
        int n2;
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        int n3 = ColorUtils.addAlpha(ColorUtils.getChromaColor(1.0f, 0), n).getRGB();
        int n4 = ColorUtils.addAlpha(ColorUtils.getChromaColor(1.0f, 3), n).getRGB();
        int n5 = ColorUtils.addAlpha(ColorUtils.getChromaColor(1.0f, 8), n).getRGB();
        int n6 = ColorUtils.addAlpha(ColorUtils.getChromaColor(1.0f, 9), n).getRGB();
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        f *= 2.0f;
        f2 *= 2.0f;
        f3 = (float)((double)f3 * 2.0);
        f4 = (float)((double)f4 * 2.0);
        GL11.glLineWidth((float)f6);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        RenderUtil.glColor(n3);
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d((double)((double)(f + f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)), (double)((double)(f2 + f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)));
        }
        RenderUtil.glColor(n4);
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d((double)((double)(f + f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)), (double)((double)(f4 - f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)(f5 * -1.0f)));
        }
        RenderUtil.glColor(n5);
        for (n2 = 0; n2 <= 90; n2 += 3) {
            GL11.glVertex2d((double)((double)(f3 - f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)f5), (double)((double)(f4 - f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)f5));
        }
        RenderUtil.glColor(n6);
        for (n2 = 90; n2 <= 180; n2 += 3) {
            GL11.glVertex2d((double)((double)(f3 - f5) + Math.sin((double)n2 * Math.PI / 180.0) * (double)f5), (double)((double)(f2 + f5) + Math.cos((double)n2 * Math.PI / 180.0) * (double)f5));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7424);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void pre() {
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
    }

    public static void post() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
    }

    public static void startDrawing() {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
    }

    public static void stopDrawing() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static Color blend(Color color, Color color2, double d) {
        float f = (float)d;
        float f2 = 1.0f - f;
        float[] fArray = new float[3];
        float[] fArray2 = new float[3];
        color.getColorComponents(fArray);
        color2.getColorComponents(fArray2);
        Color color3 = new Color(fArray[0] * f + fArray2[0] * f2, fArray[1] * f + fArray2[1] * f2, fArray[2] * f + fArray2[2] * f2);
        return color3;
    }

    public static void drawLine(Vec2f vec2f, Vec2f vec2f2, float f) {
        RenderUtil.drawLine(vec2f.getX(), vec2f.getY(), vec2f2.getX(), vec2f2.getY(), f);
    }

    public static void drawLine(Vec3f vec3f, Vec3f vec3f2, float f) {
        RenderUtil.drawLine((float)vec3f.getX(), (float)vec3f.getY(), (float)vec3f.getZ(), (float)vec3f2.getX(), (float)vec3f2.getY(), (float)vec3f2.getZ(), f);
    }

    public static void drawLine(float f, float f2, float f3, float f4, float f5) {
        RenderUtil.drawLine(f, f2, 0.0f, f3, f4, 0.0f, f5);
    }

    public static void drawLine(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        GL11.glPushMatrix();
        GL11.glLineWidth((float)f7);
        RenderUtil.setupRender(true);
        RenderUtil.setupClientState(GLClientState.VERTEX, true);
        tessellator.addVertex(f, f2, f3).addVertex(f4, f5, f6).draw(3);
        RenderUtil.setupClientState(GLClientState.VERTEX, false);
        RenderUtil.setupRender(false);
        GL11.glPopMatrix();
    }

    public static void drawLines(ArrayList arrayList, float f, float f2) {
        Entity entity = mc.func_175606_aa();
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)f2;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)f2;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)f2;
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)(-d), (double)(-d2), (double)(-d3));
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GL11.glDisable((int)3553);
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GL11.glLineWidth((float)f);
        GlStateManager.func_179097_i();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        for (int i = 0; i < arrayList.size(); ++i) {
            Vec3d vec3d = (Vec3d)arrayList.get(i);
            int n = ColorUtils.getChroma(0.75f, i);
            worldRenderer.func_181662_b(vec3d.getX(), vec3d.getY(), vec3d.getZ()).func_181666_a((float)(n >> 16 & 0xFF) / 255.0f, (float)(n >> 8 & 0xFF) / 255.0f, (float)(n & 0xFF) / 255.0f, (float)(n >> 24 & 0xFF) / 255.0f).func_181675_d();
        }
        Tessellator.func_178181_a().func_78381_a();
        GlStateManager.func_179137_b((double)d, (double)d2, (double)d3);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
    }

    public static void setupClientState(GLClientState gLClientState, boolean bl) {
        csBuffer.clear();
        if (gLClientState.ordinal() > 0) {
            csBuffer.add(gLClientState.getCap());
        }
        csBuffer.add(32884);
        csBuffer.forEach(bl ? ENABLE_CLIENT_STATE : DISABLE_CLIENT_STATE);
    }

    public static void setupRender(boolean bl) {
        if (bl) {
            GlStateManager.func_179147_l();
            GL11.glEnable((int)2848);
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            GlStateManager.func_179112_b((int)770, (int)771);
            GL11.glHint((int)3154, (int)4354);
        } else {
            GlStateManager.func_179084_k();
            GlStateManager.func_179098_w();
            GL11.glDisable((int)2848);
            GlStateManager.func_179126_j();
        }
        GlStateManager.func_179132_a((!bl ? 1 : 0) != 0);
    }

    public static void entityOutlineAXIS(Entity entity, int n, EventRender3D eventRender3D) {
        RenderUtil.drawOutlinedBoundingBox(entity.func_174813_aQ().func_72314_b(0.15, 0.15, 0.15), n, 2.0f, eventRender3D.getPartialTicks());
    }

    public static void setColor(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)(f == 0.0f ? 1.0f : f));
    }

    public static void enableGL3D(float f) {
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2884);
        GlStateManager.func_179140_f();
        GlStateManager.func_179106_n();
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glLineWidth((float)f);
    }

    public static void disableGL3D() {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDepthMask((boolean)true);
        GL11.glCullFace((int)1029);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void draw3DLine(Vec3f vec3f, Vec3f vec3f2, Color color, float f) {
        RenderUtil.draw3DLine(vec3f.getX(), vec3f.getY(), vec3f.getZ(), vec3f2.getX(), vec3f2.getY(), vec3f2.getZ(), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), f);
    }

    public static void draw3DLine(double d, double d2, double d3, double d4, double d5, double d6, float f, float f2, float f3, float f4, float f5) {
        d -= RenderUtil.mc.func_175598_ae().field_78730_l;
        d4 -= RenderUtil.mc.func_175598_ae().field_78730_l;
        d2 -= RenderUtil.mc.func_175598_ae().field_78731_m;
        d5 -= RenderUtil.mc.func_175598_ae().field_78731_m;
        d3 -= RenderUtil.mc.func_175598_ae().field_78728_n;
        d6 -= RenderUtil.mc.func_175598_ae().field_78728_n;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)f5);
        GL11.glColor4f((float)f, (float)f2, (float)f3, (float)f4);
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glVertex3d((double)d4, (double)d5, (double)d6);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidBlockESP(BlockPos blockPos, int n, float f) {
        RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p(), (double)(blockPos.func_177958_n() + 1), (double)(blockPos.func_177956_o() + 1), (double)(blockPos.func_177952_p() + 1)), n, 2.0f, f);
    }

    public static void drawSolidBlockESP(BlockPos blockPos, int n, float f, float f2) {
        RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p(), (double)(blockPos.func_177958_n() + 1), (double)(blockPos.func_177956_o() + 1), (double)(blockPos.func_177952_p() + 1)), n, f, f2);
    }

    public static void drawFullBlockESP(BlockPos blockPos, Color color, float f) {
        Entity entity = Minecraft.func_71410_x().func_175606_aa();
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)f;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)f;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)f;
        double d4 = (double)blockPos.func_177958_n() - d;
        double d5 = (double)blockPos.func_177956_o() - d2;
        double d6 = (double)blockPos.func_177952_p() - d3;
        GlStateManager.func_179117_G();
        GlStateManager.func_179097_i();
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtil.glColor(color);
        RenderUtil.dbb(new AxisAlignedBB(d4, d5, d6, d4 + 1.0, d5 + 1.0, d6 + 1.0), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
        GlStateManager.func_179126_j();
        GlStateManager.func_179117_G();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB, int n, float f, float f2) {
        Entity entity = Minecraft.func_71410_x().func_175606_aa();
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)f2;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)f2;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)f2;
        GlStateManager.func_179117_G();
        GlStateManager.func_179097_i();
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)(-d), (double)(-d2), (double)(-d3));
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GL11.glLineWidth((float)f);
        RenderUtil.glColor(n);
        RenderUtil.drawOutlinedBoundingBox(axisAlignedBB);
        GL11.glDisable((int)2848);
        GlStateManager.func_179137_b((double)d, (double)d2, (double)d3);
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179121_F();
        GlStateManager.func_179126_j();
        GlStateManager.func_179117_G();
    }

    private static void dbb(AxisAlignedBB axisAlignedBB, float f, float f2, float f3, float f4) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f /= 255.0f, f2 /= 255.0f, f3 /= 255.0f, f4 /= 255.0f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181666_a(f, f2, f3, f4).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawFastRoundedRect(float f, float f2, float f3, float f4, float f5, int n) {
        if (f == f3 || f2 == f4) {
            return;
        }
        int n2 = 18;
        float f6 = 90.0f / (float)n2;
        if (Math.abs(f4 - f2) < 2.0f * f5) {
            f5 = Math.abs(f4 - f2) / 2.0f;
        }
        if (Math.abs(f3 - f) < 2.0f * f5) {
            f5 = Math.abs(f3 - f) / 2.0f;
        }
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glPushMatrix();
        RenderUtil.glColor(n);
        GL11.glBegin((int)5);
        GL11.glVertex2f((float)(f + f5), (float)f2);
        GL11.glVertex2f((float)(f + f5), (float)f4);
        GL11.glVertex2f((float)(f3 - f5), (float)f2);
        GL11.glVertex2f((float)(f3 - f5), (float)f4);
        GL11.glEnd();
        GL11.glBegin((int)5);
        GL11.glVertex2f((float)f, (float)(f2 + f5));
        GL11.glVertex2f((float)(f + f5), (float)(f2 + f5));
        GL11.glVertex2f((float)f, (float)(f4 - f5));
        GL11.glVertex2f((float)(f + f5), (float)(f4 - f5));
        GL11.glEnd();
        GL11.glBegin((int)5);
        GL11.glVertex2f((float)f3, (float)(f2 + f5));
        GL11.glVertex2f((float)(f3 - f5), (float)(f2 + f5));
        GL11.glVertex2f((float)f3, (float)(f4 - f5));
        GL11.glVertex2f((float)(f3 - f5), (float)(f4 - f5));
        GL11.glEnd();
        if (f5 > 0.0f) {
            float f7;
            GL11.glBegin((int)6);
            float f8 = f3 - f5;
            float f9 = f2 + f5;
            GL11.glVertex2f((float)f8, (float)f9);
            int n3 = 0;
            for (n3 = 0; n3 <= n2; ++n3) {
                f7 = (float)n3 * f6;
                GL11.glVertex2f((float)((float)((double)f8 + (double)f5 * Math.cos(Math.toRadians(f7)))), (float)((float)((double)f9 - (double)f5 * Math.sin(Math.toRadians(f7)))));
            }
            GL11.glEnd();
            GL11.glBegin((int)6);
            f8 = f + f5;
            f9 = f2 + f5;
            GL11.glVertex2f((float)f8, (float)f9);
            for (n3 = 0; n3 <= n2; ++n3) {
                f7 = (float)n3 * f6;
                GL11.glVertex2f((float)((float)((double)f8 - (double)f5 * Math.cos(Math.toRadians(f7)))), (float)((float)((double)f9 - (double)f5 * Math.sin(Math.toRadians(f7)))));
            }
            GL11.glEnd();
            GL11.glBegin((int)6);
            f8 = f + f5;
            f9 = f4 - f5;
            GL11.glVertex2f((float)f8, (float)f9);
            for (n3 = 0; n3 <= n2; ++n3) {
                f7 = (float)n3 * f6;
                GL11.glVertex2f((float)((float)((double)f8 - (double)f5 * Math.cos(Math.toRadians(f7)))), (float)((float)((double)f9 + (double)f5 * Math.sin(Math.toRadians(f7)))));
            }
            GL11.glEnd();
            GL11.glBegin((int)6);
            f8 = f3 - f5;
            f9 = f4 - f5;
            GL11.glVertex2f((float)f8, (float)f9);
            for (n3 = 0; n3 <= n2; ++n3) {
                f7 = (float)n3 * f6;
                GL11.glVertex2f((float)((float)((double)f8 + (double)f5 * Math.cos(Math.toRadians(f7)))), (float)((float)((double)f9 + (double)f5 * Math.sin(Math.toRadians(f7)))));
            }
            GL11.glEnd();
        }
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)3042);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void prepareScissorBox(float f, float f2, float f3, float f4) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        int n = scaledResolution.func_78325_e();
        GL11.glScissor((int)((int)(f * (float)n)), (int)((int)(((float)scaledResolution.func_78328_b() - f4) * (float)n)), (int)((int)((f3 - f) * (float)n)), (int)((int)((f4 - f2) * (float)n)));
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void glColor(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
    }

    public static void drawFilledESP(Entity entity, Color color, EventRender3D eventRender3D) {
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78730_l;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78731_m;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78728_n;
        double d4 = entity.func_174813_aQ().field_72336_d - entity.func_174813_aQ().field_72340_a - (double)0.1f;
        double d5 = entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b + (double)0.2f;
        RenderUtil.drawEntityESP(d, d2, d3, d4, d5, 0.0f, 0.0f, 0.0f, 0.0f, (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f, 1.6f, eventRender3D.getPartialTicks());
    }

    public static void drawFilledESP(Entity entity, Color color, EventRender3D eventRender3D, float f) {
        double d = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78730_l;
        double d2 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78731_m;
        double d3 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)eventRender3D.getPartialTicks() - RenderUtil.mc.func_175598_ae().field_78728_n;
        double d4 = entity.func_174813_aQ().field_72336_d - entity.func_174813_aQ().field_72340_a - (double)0.1f;
        double d5 = entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b + (double)0.2f;
        RenderUtil.drawEntityESP(d, d2, d3, d4, d5, 0.0f, 0.0f, 0.0f, 0.0f, (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f, f, eventRender3D.getPartialTicks());
    }

    public static void drawEntityESP(double d, double d2, double d3, double d4, double d5, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)f, (float)f2, (float)f3, (float)f4);
        RenderUtil.drawBoundingBox(new AxisAlignedBB(d - d4, d2, d3 - d4, d + d4, d2 + d5, d3 + d4));
        GL11.glLineWidth((float)f9);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f8);
        RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(d - d4, d2, d3 - d4, d + d4, d2 + d5, d3 + d4));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179117_G();
    }

    public static void drawBoundingBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static Color rainbow(long l2, float f, float f2) {
        float f3 = ((float)l2 + (-10.0f + f) * 2.0E8f) / 3.0E9f % -360.0f;
        long l3 = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(f3, 0.35f, 1.0f)), 16);
        Color color = new Color((int)l3);
        return new Color((float)color.getRed() / 255.0f * f2, (float)color.getGreen() / 255.0f * f2, (float)color.getBlue() / 255.0f * f2, (float)color.getAlpha() / 255.0f);
    }

    public static void drawFilledCircle(float f, float f2, float f3, Color color) {
        int n = 180;
        double d = Math.PI * 2 / (double)n;
        GlStateManager.func_179123_a();
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2881);
        GL11.glBegin((int)6);
        for (int i = 0; i < n; ++i) {
            float f4 = (float)((double)f3 * Math.sin((double)i * d));
            float f5 = (float)((double)f3 * Math.cos((double)i * d));
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(f + f4), (float)(f2 + f5));
        }
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
        GlStateManager.func_179099_b();
    }

    public static void drawSwitcherBG(float f, float f2, float f3, Color color) {
        float f4;
        float f5;
        int n;
        int n2 = 180;
        double d = Math.PI / (double)n2;
        GlStateManager.func_179123_a();
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)6);
        RenderUtil.glColor(color.getRGB());
        for (n = 0; n < n2; ++n) {
            f5 = (float)((double)f3 * Math.sin((double)n * d));
            f4 = (float)((double)f3 * Math.cos((double)n * d));
            GL11.glVertex2f((float)(f + f5 + 7.0f), (float)(f2 + f4));
        }
        GL11.glEnd();
        GL11.glDisable((int)2884);
        GL11.glBegin((int)5);
        RenderUtil.glColor(color.getRGB());
        GL11.glVertex2f((float)(f - f3 - 1.0f), (float)(f2 + f3));
        GL11.glVertex2f((float)(f - f3 - 1.0f), (float)(f2 + f3 - f3 * 2.0f));
        GL11.glVertex2f((float)(f + f3 + 1.0f), (float)(f2 + f3));
        GL11.glVertex2f((float)(f + f3 + 1.0f), (float)(f2 + f3 - f3 * 2.0f));
        GL11.glEnd();
        GL11.glEnable((int)2884);
        GL11.glBegin((int)6);
        RenderUtil.glColor(color.getRGB());
        for (n = 0; n < n2; ++n) {
            f5 = -((float)((double)f3 * Math.sin((double)n * d)));
            f4 = -((float)((double)f3 * Math.cos((double)n * d)));
            GL11.glVertex2f((float)(f + f5 - 7.0f), (float)(f2 + f4));
        }
        GL11.glEnd();
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GlStateManager.func_179099_b();
    }

    public static void drawPlayerIcon(EntityPlayer entityPlayer, int n, int n2, int n3) {
        if (entityPlayer != null) {
            mc.func_110434_K().func_110577_a(mc.func_147114_u().func_175102_a(entityPlayer.func_110124_au()).func_178837_g());
            Gui.func_152125_a((int)n2, (int)n3, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)n, (int)n, (float)64.0f, (float)64.0f);
            if (entityPlayer.func_175148_a(EnumPlayerModelParts.HAT)) {
                Gui.func_152125_a((int)n2, (int)n3, (float)40.0f, (float)8.0f, (int)8, (int)8, (int)n, (int)n, (float)64.0f, (float)64.0f);
            }
        }
    }

    public static void drawOnSlot(int n, int n2, int n3, int n4) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        int n5 = (scaledResolution.func_78326_a() - 176) / 2;
        int n6 = (scaledResolution.func_78328_b() - 222) / 2;
        int n7 = n5 + n2;
        int n8 = n6 + n3;
        if (n != 90) {
            n8 += (6 - (n - 36) / 9) * 9;
        }
        GL11.glTranslated((double)0.0, (double)0.0, (double)1.0);
        Gui.func_73734_a((int)n7, (int)n8, (int)(n7 + 16), (int)(n8 + 16), (int)n4);
        GL11.glTranslated((double)0.0, (double)0.0, (double)-1.0);
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void drawItemStack(ItemStack itemStack, int n, int n2) {
        if (itemStack == null) {
            return;
        }
        RenderUtil.drawItemStackWithText(itemStack, n, n2, null);
    }

    public static void drawItemStackWithText(ItemStack itemStack, int n, int n2, String string) {
        if (itemStack == null) {
            return;
        }
        RenderItem renderItem = Minecraft.func_71410_x().func_175599_af();
        RenderHelper.func_74520_c();
        renderItem.field_77023_b = -145.0f;
        renderItem.func_180450_b(itemStack, n, n2);
        renderItem.func_180453_a(Minecraft.func_71410_x().field_71466_p, itemStack, n, n2, string);
        renderItem.field_77023_b = 0.0f;
        RenderHelper.func_74518_a();
    }

    public static void renderBeacon(BlockPos blockPos, Color color, float f) {
        new Beacon(new Vec3((Vec3i)blockPos), color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), false).render(f);
    }

    public static void trace(BlockPos blockPos, int n) {
        double d = (double)blockPos.func_177958_n() - RenderUtil.mc.func_175598_ae().field_78730_l + 0.5;
        double d2 = (double)blockPos.func_177956_o() - RenderUtil.mc.func_175598_ae().field_78731_m + 0.5;
        double d3 = (double)blockPos.func_177952_p() - RenderUtil.mc.func_175598_ae().field_78728_n + 0.5;
        RenderUtil.startDrawing();
        GL11.glEnable((int)2848);
        RenderUtil.setColor(n);
        GL11.glLineWidth((float)3.0f);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)RenderUtil.mc.field_71439_g.func_70047_e(), (double)0.0);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        RenderUtil.stopDrawing();
    }

    public static void renderTag(BlockPos blockPos, String string) {
        float f = (float)(RenderUtil.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) / 10.0);
        if (f < 1.1f) {
            f = 1.1f;
        }
        float f2 = f * 1.8f;
        f2 /= 100.0f;
        double d = (double)blockPos.func_177958_n() - RenderUtil.mc.func_175598_ae().field_78730_l + 0.5;
        double d2 = (double)blockPos.func_177956_o() - RenderUtil.mc.func_175598_ae().field_78731_m + 0.3;
        double d3 = (double)blockPos.func_177952_p() - RenderUtil.mc.func_175598_ae().field_78728_n + 0.5;
        GL11.glPushMatrix();
        GlStateManager.func_179117_G();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        GL11.glTranslated((double)d, (double)d2, (double)d3);
        GL11.glRotatef((float)(-RenderUtil.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)2.0f, (float)0.0f);
        GL11.glRotatef((float)RenderUtil.mc.func_175598_ae().field_78732_j, (float)2.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-f2), (float)(-f2), (float)f2);
        float f3 = (float)(-RenderUtil.mc.field_71466_p.func_78256_a(string) / 2) - 4.6f;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtil.mc.field_71466_p.func_78276_b(string, (int)f3 + 4, -13, -1);
        GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
        GL11.glDisable((int)32823);
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
    }

    public static void startTop() {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
    }

    public static void endTop() {
        GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
        GL11.glDisable((int)32823);
    }

    public static void playerTag(EntityPlayer entityPlayer, String string, int n, float f, float f2) {
        float f3 = (float)(entityPlayer.field_70142_S + (entityPlayer.field_70165_t - entityPlayer.field_70142_S) * (double)f - RenderUtil.mc.func_175598_ae().field_78730_l);
        float f4 = (float)(entityPlayer.field_70137_T + (entityPlayer.field_70163_u - entityPlayer.field_70137_T) * (double)f - RenderUtil.mc.func_175598_ae().field_78731_m);
        float f5 = (float)(entityPlayer.field_70136_U + (entityPlayer.field_70161_v - entityPlayer.field_70136_U) * (double)f - RenderUtil.mc.func_175598_ae().field_78728_n);
        float f6 = RenderUtil.mc.field_71439_g.func_70032_d((Entity)entityPlayer) / 10.0f;
        if (f6 < 1.1f) {
            f6 = 1.1f;
        }
        f4 = (float)((double)f4 + (entityPlayer.func_70093_af() ? 0.5 : 0.7));
        float f7 = f6 * 1.8f;
        f7 /= 100.0f;
        GL11.glPushMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float f8 = (Boolean)Cam.getINSTANCE().chilePlayers.getValue() != false ? 0.6f : 1.4f + f2;
        GL11.glTranslatef((float)f3, (float)(f4 + f8), (float)f5);
        GL11.glRotatef((float)(-RenderUtil.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)2.0f, (float)0.0f);
        GL11.glRotatef((float)RenderUtil.mc.func_175598_ae().field_78732_j, (float)2.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)(-f7), (float)(-f7), (float)f7);
        GLUtil.setGLCap(2929, false);
        GLUtil.setGLCap(3042, true);
        float f9 = (float)(-RenderUtil.mc.field_71466_p.func_78256_a(string) / 2) - 4.6f;
        float f10 = f9 - 2.0f * f9;
        float f11 = -16.0f;
        RenderUtil.drawFastRoundedRect(f9, f11, f10, -2.0f, 2.0f, new Color(25, 25, 25, 111).getRGB());
        RenderUtil.mc.field_71466_p.func_175065_a(string, f9 + 4.0f, -12.5f, n, false);
        GLUtil.revertAllCaps();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
    }

    static {
        mc = Minecraft.func_71410_x();
        tessellator = Tessellation.createExpanding(4, 1.0f, 2.0f);
        csBuffer = new ArrayList();
        ENABLE_CLIENT_STATE = GL11::glEnableClientState;
        DISABLE_CLIENT_STATE = GL11::glEnableClientState;
    }
}

