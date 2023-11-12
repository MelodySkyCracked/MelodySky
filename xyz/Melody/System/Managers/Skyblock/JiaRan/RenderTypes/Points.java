/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.System.Managers.Skyblock.JiaRan.RenderTypes;

import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.Melody.System.Managers.Skyblock.JiaRan.MythlogicalManager;
import xyz.Melody.System.Managers.Skyblock.JiaRan.RenderAble;

public class Points
implements RenderAble {
    public ArrayList points;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public float thickness;
    public boolean depthtest;
    public int glmode;
    public boolean disableCullFace;

    public Points(ArrayList arrayList, float f, float f2, float f3, float f4, float f5, boolean bl) {
        this.points = arrayList;
        this.red = f;
        this.green = f2;
        this.blue = f3;
        this.alpha = f4;
        this.thickness = f5;
        this.depthtest = bl;
        this.glmode = 2;
        this.disableCullFace = false;
    }

    @Override
    public void render(float f) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)this.thickness);
        GL11.glDisable((int)3553);
        if (!this.depthtest) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        if (this.disableCullFace) {
            GL11.glDisable((int)2884);
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179137_b((double)(-Points.mc.func_175598_ae().field_78730_l), (double)(-Points.mc.func_175598_ae().field_78731_m), (double)(-Points.mc.func_175598_ae().field_78728_n));
        MythlogicalManager.instance.worldRenderer.func_181668_a(this.glmode, DefaultVertexFormats.field_181705_e);
        GlStateManager.func_179131_c((float)this.red, (float)this.green, (float)this.blue, (float)this.alpha);
        for (Vec3 vec3 : this.points) {
            MythlogicalManager.instance.worldRenderer.func_181662_b(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c).func_181675_d();
        }
        MythlogicalManager.instance.tessellator.func_78381_a();
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
        GL11.glEnable((int)3553);
        if (this.disableCullFace) {
            GL11.glEnable((int)2884);
        }
        if (!this.depthtest) {
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
        }
        GL11.glDisable((int)3042);
    }
}

