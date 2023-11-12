/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.System.Managers.Skyblock.JiaRan.RenderTypes;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.Melody.System.Managers.Skyblock.JiaRan.MythlogicalManager;
import xyz.Melody.System.Managers.Skyblock.JiaRan.RenderAble;

public class Beacon
implements RenderAble {
    private static ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
    public Vec3 location;
    public float red;
    public float green;
    public float blue;
    public float alpha;
    public boolean depthtest;

    public Beacon(Vec3 vec3, float f, float f2, float f3, float f4, boolean bl) {
        this.location = vec3;
        this.red = f;
        this.green = f2;
        this.blue = f3;
        this.alpha = f4;
        this.depthtest = bl;
    }

    @Override
    public void render(float f) {
        int n = 300;
        int n2 = 0;
        int n3 = n2 + n;
        GlStateManager.func_179094_E();
        if (!this.depthtest) {
            GlStateManager.func_179097_i();
        }
        GlStateManager.func_179137_b((double)(-Beacon.mc.func_175598_ae().field_78730_l), (double)(-Beacon.mc.func_175598_ae().field_78731_m), (double)(-Beacon.mc.func_175598_ae().field_78728_n));
        mc.func_110434_K().func_110577_a(beaconBeam);
        GL11.glTexParameterf((int)3553, (int)10242, (float)10497.0f);
        GL11.glTexParameterf((int)3553, (int)10243, (float)10497.0f);
        GlStateManager.func_179140_f();
        GlStateManager.func_179089_o();
        GlStateManager.func_179098_w();
        GlStateManager.func_179120_a((int)770, (int)1, (int)1, (int)0);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        double d = (float)Beacon.mc.field_71441_e.func_72820_D() + f;
        double d2 = MathHelper.func_181162_h((double)(-d * 0.2 - (double)MathHelper.func_76128_c((double)(-d * 0.1))));
        double d3 = d * 0.025 * -1.5;
        double d4 = 0.5 + Math.cos(d3 + 2.356194490192345) * 0.2;
        double d5 = 0.5 + Math.sin(d3 + 2.356194490192345) * 0.2;
        double d6 = 0.5 + Math.cos(d3 + 0.7853981633974483) * 0.2;
        double d7 = 0.5 + Math.sin(d3 + 0.7853981633974483) * 0.2;
        double d8 = 0.5 + Math.cos(d3 + 3.9269908169872414) * 0.2;
        double d9 = 0.5 + Math.sin(d3 + 3.9269908169872414) * 0.2;
        double d10 = 0.5 + Math.cos(d3 + 5.497787143782138) * 0.2;
        double d11 = 0.5 + Math.sin(d3 + 5.497787143782138) * 0.2;
        double d12 = -1.0 + d2;
        double d13 = (double)n * 2.5 + d12;
        double d14 = this.location.field_72450_a;
        double d15 = this.location.field_72448_b;
        double d16 = this.location.field_72449_c;
        MythlogicalManager.instance.worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d4, d15 + (double)n3, d16 + d5).func_181673_a(1.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d4, d15 + (double)n2, d16 + d5).func_181673_a(1.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d6, d15 + (double)n2, d16 + d7).func_181673_a(0.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d6, d15 + (double)n3, d16 + d7).func_181673_a(0.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d10, d15 + (double)n3, d16 + d11).func_181673_a(1.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d10, d15 + (double)n2, d16 + d11).func_181673_a(1.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d8, d15 + (double)n2, d16 + d9).func_181673_a(0.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d8, d15 + (double)n3, d16 + d9).func_181673_a(0.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d6, d15 + (double)n3, d16 + d7).func_181673_a(1.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d6, d15 + (double)n2, d16 + d7).func_181673_a(1.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d10, d15 + (double)n2, d16 + d11).func_181673_a(0.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d10, d15 + (double)n3, d16 + d11).func_181673_a(0.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d8, d15 + (double)n3, d16 + d9).func_181673_a(1.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d8, d15 + (double)n2, d16 + d9).func_181673_a(1.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d4, d15 + (double)n2, d16 + d5).func_181673_a(0.0, d12).func_181666_a(this.red, this.green, this.blue, 1.0f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + d4, d15 + (double)n3, d16 + d5).func_181673_a(0.0, d13).func_181666_a(this.red, this.green, this.blue, 1.0f * this.alpha).func_181675_d();
        MythlogicalManager.instance.tessellator.func_78381_a();
        GlStateManager.func_179129_p();
        double d17 = -1.0 + d2;
        double d18 = (double)n + d17;
        MythlogicalManager.instance.worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n3, d16 + 0.2).func_181673_a(1.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n2, d16 + 0.2).func_181673_a(1.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n2, d16 + 0.2).func_181673_a(0.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n3, d16 + 0.2).func_181673_a(0.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n3, d16 + 0.8).func_181673_a(1.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n2, d16 + 0.8).func_181673_a(1.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n2, d16 + 0.8).func_181673_a(0.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n3, d16 + 0.8).func_181673_a(0.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n3, d16 + 0.2).func_181673_a(1.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n2, d16 + 0.2).func_181673_a(1.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n2, d16 + 0.8).func_181673_a(0.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.8, d15 + (double)n3, d16 + 0.8).func_181673_a(0.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n3, d16 + 0.8).func_181673_a(1.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n2, d16 + 0.8).func_181673_a(1.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n2, d16 + 0.2).func_181673_a(0.0, d17).func_181666_a(this.red, this.green, this.blue, 0.25f).func_181675_d();
        MythlogicalManager.instance.worldRenderer.func_181662_b(d14 + 0.2, d15 + (double)n3, d16 + 0.2).func_181673_a(0.0, d18).func_181666_a(this.red, this.green, this.blue, 0.25f * this.alpha).func_181675_d();
        MythlogicalManager.instance.tessellator.func_78381_a();
        GlStateManager.func_179140_f();
        GlStateManager.func_179098_w();
        if (!this.depthtest) {
            GlStateManager.func_179126_j();
        }
        GlStateManager.func_179121_F();
    }
}

