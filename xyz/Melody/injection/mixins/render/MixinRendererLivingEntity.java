/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.entity.RendererLivingEntity
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.MathHelper
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 *  net.minecraftforge.client.event.RenderLivingEvent$Pre
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package xyz.Melody.injection.mixins.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.rendering.EventRenderEntityModel;
import xyz.Melody.injection.mixins.render.MixinRender;
import xyz.Melody.module.modules.render.Cam;

@Mixin(value={RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity
extends MixinRender {
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    protected boolean field_177098_i;
    @Shadow
    private static final Logger field_147923_a = LogManager.getLogger();

    @Shadow
    protected abstract float func_77040_d(EntityLivingBase var1, float var2);

    @Shadow
    protected abstract float func_77034_a(float var1, float var2, float var3);

    @Shadow
    protected abstract void func_77039_a(EntityLivingBase var1, double var2, double var4, double var6);

    @Shadow
    protected abstract float func_77044_a(EntityLivingBase var1, float var2);

    @Shadow
    protected abstract void func_77043_a(EntityLivingBase var1, float var2, float var3, float var4);

    @Shadow
    protected abstract void func_77041_b(EntityLivingBase var1, float var2);

    @Shadow
    protected abstract void func_77036_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7);

    @Shadow
    protected abstract void func_177093_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    @Shadow
    protected abstract boolean func_177088_c(EntityLivingBase var1);

    @Shadow
    protected abstract void func_180565_e();

    @Shadow
    protected abstract boolean func_177090_c(EntityLivingBase var1, float var2);

    @Shadow
    protected abstract void func_177091_f();

    @Inject(method="renderModel", at={@At(value="HEAD")}, cancellable=true)
    private void renderModel(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, CallbackInfo callbackInfo) {
        EventRenderEntityModel eventRenderEntityModel = new EventRenderEntityModel(entityLivingBase, f, f2, f3, f4, f5, f6, this.field_77045_g, this.field_177098_i);
        EventBus.getInstance().call(eventRenderEntityModel);
        this.field_177098_i = eventRenderEntityModel.isOutline();
        if (eventRenderEntityModel.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Overwrite
    public void func_76986_a(EntityLivingBase entityLivingBase, double d, double d2, double d3, float f, float f2) {
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre(entityLivingBase, (RendererLivingEntity)this, d, d2, d3))) {
            return;
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179129_p();
        this.field_77045_g.field_78095_p = this.func_77040_d(entityLivingBase, f2);
        this.field_77045_g.field_78093_q = entityLivingBase.func_70115_ae();
        if (entityLivingBase.shouldRiderSit()) {
            this.field_77045_g.field_78093_q = entityLivingBase.func_70115_ae() && entityLivingBase.field_70154_o != null && entityLivingBase.field_70154_o.shouldRiderSit();
        }
        this.field_77045_g.field_78091_s = (Boolean)Cam.getINSTANCE().chilePlayers.getValue() != false && entityLivingBase instanceof EntityPlayer ? true : entityLivingBase.func_70631_g_();
        try {
            float f3;
            float f4 = this.func_77034_a(entityLivingBase.field_70760_ar, entityLivingBase.field_70761_aq, f2);
            float f5 = this.func_77034_a(entityLivingBase.field_70758_at, entityLivingBase.field_70759_as, f2);
            float f6 = f5 - f4;
            if (this.field_77045_g.field_78093_q && entityLivingBase.field_70154_o instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase2 = (EntityLivingBase)entityLivingBase.field_70154_o;
                f4 = this.func_77034_a(entityLivingBase2.field_70760_ar, entityLivingBase2.field_70761_aq, f2);
                f6 = f5 - f4;
                f3 = MathHelper.func_76142_g((float)f6);
                if (f3 < -85.0f) {
                    f3 = -85.0f;
                }
                if (f3 >= 85.0f) {
                    f3 = 85.0f;
                }
                f4 = f5 - f3;
                if (f3 * f3 > 2500.0f) {
                    f4 += f3 * 0.2f;
                }
            }
            float f7 = entityLivingBase == Minecraft.func_71410_x().field_71439_g ? Client.instance.prevRotationPitchHead + (Client.instance.rotationPitchHead - Client.instance.prevRotationPitchHead) * f2 : entityLivingBase.field_70127_C + (entityLivingBase.field_70125_A - entityLivingBase.field_70127_C) * f2;
            this.func_77039_a(entityLivingBase, d, d2, d3);
            f3 = this.func_77044_a(entityLivingBase, f2);
            this.func_77043_a(entityLivingBase, f3, f4, f2);
            GlStateManager.func_179091_B();
            GlStateManager.func_179152_a((float)-1.0f, (float)-1.0f, (float)1.0f);
            this.func_77041_b(entityLivingBase, f2);
            GlStateManager.func_179109_b((float)0.0f, (float)-1.5078125f, (float)0.0f);
            float f8 = entityLivingBase.field_70722_aY + (entityLivingBase.field_70721_aZ - entityLivingBase.field_70722_aY) * f2;
            float f9 = entityLivingBase.field_70754_ba - entityLivingBase.field_70721_aZ * (1.0f - f2);
            if (entityLivingBase.func_70631_g_()) {
                f9 *= 3.0f;
            }
            if (f8 > 1.0f) {
                f8 = 1.0f;
            }
            GlStateManager.func_179141_d();
            this.field_77045_g.func_78086_a(entityLivingBase, f9, f8, f2);
            this.field_77045_g.func_78087_a(f9, f8, f3, f6, f7, 0.0625f, (Entity)entityLivingBase);
            this.func_77036_a(entityLivingBase, f9, f8, f3, f6, f7, 0.0625f);
            if (this.field_177098_i) {
                boolean bl = this.func_177088_c(entityLivingBase);
                this.func_77036_a(entityLivingBase, f9, f8, f3, f6, f7, 0.0625f);
                if (bl) {
                    this.func_180565_e();
                }
            } else {
                boolean bl = this.func_177090_c(entityLivingBase, f2);
                this.func_77036_a(entityLivingBase, f9, f8, f3, f6, f7, 0.0625f);
                if (bl) {
                    this.func_177091_f();
                }
                GlStateManager.func_179132_a((boolean)true);
                if (!(entityLivingBase instanceof EntityPlayer) || !((EntityPlayer)entityLivingBase).func_175149_v()) {
                    this.func_177093_a(entityLivingBase, f9, f8, f2, f3, f6, f7, 0.0625f);
                }
            }
            GlStateManager.func_179101_C();
        }
        catch (Exception exception) {
            field_147923_a.error("Couldn't render entity", (Throwable)exception);
        }
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179098_w();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        GlStateManager.func_179089_o();
        GlStateManager.func_179121_F();
        if (!this.field_177098_i) {
            super.func_76986_a((Entity)entityLivingBase, d, d2, d3, f, f2);
        }
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post(entityLivingBase, (RendererLivingEntity)this, d, d2, d3));
    }

    @Override
    @Overwrite
    public void func_76986_a(Entity entity, double d, double d2, double d3, float f, float f2) {
        this.func_76986_a((EntityLivingBase)entity, d, d2, d3, f, f2);
    }
}

