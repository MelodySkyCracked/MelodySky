/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.Vec3
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.System.Managers.Skyblock.JiaRan.RenderTypes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.Melody.System.Managers.Skyblock.JiaRan.RenderAble;

public class WorldText
implements RenderAble {
    public Vec3 location;
    public String text;
    public boolean depthtest;
    public float scale;
    public boolean shadow;

    public WorldText(Vec3 vec3, String string, boolean bl, float f) {
        this.location = vec3;
        this.text = string;
        this.depthtest = bl;
        this.scale = f;
        this.shadow = false;
    }

    @Override
    public void render(float f) {
        if (!this.depthtest) {
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179137_b((double)(this.location.field_72450_a - WorldText.mc.func_175598_ae().field_78730_l), (double)(this.location.field_72448_b - WorldText.mc.func_175598_ae().field_78731_m), (double)(this.location.field_72449_c - WorldText.mc.func_175598_ae().field_78728_n));
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
        GlStateManager.func_179114_b((float)(-WorldText.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)WorldText.mc.func_175598_ae().field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179152_a((float)(-this.scale / 25.0f), (float)(-this.scale / 25.0f), (float)(this.scale / 25.0f));
        if (this.shadow) {
            Minecraft.func_71410_x().field_71466_p.func_175063_a(this.text, (float)(-WorldText.mc.field_71466_p.func_78256_a(this.text) / 2), 0.0f, 0);
        } else {
            Minecraft.func_71410_x().field_71466_p.func_78276_b(this.text, -WorldText.mc.field_71466_p.func_78256_a(this.text) / 2, 0, 0);
        }
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179084_k();
        GlStateManager.func_179121_F();
        if (!this.depthtest) {
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
        }
    }
}

