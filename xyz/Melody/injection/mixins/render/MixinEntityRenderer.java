/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 *  com.google.common.base.Predicates
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.shader.ShaderGroup
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EntitySelectors
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package xyz.Melody.injection.mixins.render;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.GUI.Notification.NotificationPublisher;
import xyz.Melody.module.balance.Reach;
import xyz.Melody.module.modules.render.Cam;
import xyz.Melody.module.modules.render.MotionBlur;

@SideOnly(value=Side.CLIENT)
@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer {
    @Shadow
    private Entity field_78528_u;
    @Shadow
    private ShaderGroup field_147707_d;
    @Shadow
    private boolean field_175083_ad;
    @Shadow
    private Minecraft field_78531_r;
    @Shadow
    private float field_78491_C;
    @Shadow
    private float field_78490_B;
    @Shadow
    private boolean field_78500_U;

    @Inject(method="updateCameraAndRender", at={@At(value="RETURN")})
    private void postUpdateCameraAndRender(float f, long l2, CallbackInfo callbackInfo) {
        NotificationPublisher.publish(new ScaledResolution(this.field_78531_r));
    }

    @Inject(method="updateCameraAndRender", at={@At(value="INVOKE", target="Lnet/minecraft/client/shader/Framebuffer;bindFramebuffer(Z)V", shift=At.Shift.BEFORE)})
    public void updateCameraAndRender(float f, long l2, CallbackInfo callbackInfo) {
        ArrayList<ShaderGroup> arrayList = new ArrayList<ShaderGroup>();
        if (this.field_147707_d != null && this.field_175083_ad) {
            arrayList.add(this.field_147707_d);
        }
        ShaderGroup shaderGroup = MotionBlur.getINSTANCE().getShader();
        if (MotionBlur.getINSTANCE().isEnabled()) {
            if (shaderGroup != null) {
                arrayList.add(shaderGroup);
            }
            for (ShaderGroup shaderGroup2 : arrayList) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179096_D();
                shaderGroup2.func_148018_a(f);
                GlStateManager.func_179121_F();
            }
        }
    }

    @Inject(method="updateShaderGroupSize", at={@At(value="RETURN")})
    public void updateShaderGroupSize(int n, int n2, CallbackInfo callbackInfo) {
        ShaderGroup shaderGroup;
        if (this.field_78531_r.field_71441_e != null && OpenGlHelper.field_148824_g && (shaderGroup = MotionBlur.getINSTANCE().getShader()) != null) {
            shaderGroup.func_148026_a(n, n2);
        }
    }

    @Inject(method="renderWorldPass", at={@At(value="FIELD", target="Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift=At.Shift.BEFORE)})
    private void renderWorldPass(int n, float f, long l2, CallbackInfo callbackInfo) {
        EventRender3D eventRender3D = new EventRender3D(f);
        EventBus.getInstance().call(eventRender3D);
    }

    @Inject(method="hurtCameraEffect", at={@At(value="HEAD")}, cancellable=true)
    private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
        if (Cam.getINSTANCE().isEnabled() && ((Boolean)Cam.getINSTANCE().bht.getValue()).booleanValue()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method="orientCamera", at=@At(value="INVOKE", target="Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal=0))
    private double distanceTo$cameraClip(Vec3 vec3, Vec3 vec32) {
        if (Cam.getINSTANCE().isEnabled() && ((Boolean)Cam.getINSTANCE().noClip.getValue()).booleanValue()) {
            return 100.0;
        }
        return vec3.func_72438_d(vec32);
    }

    @Inject(method="getMouseOver", at={@At(value="HEAD")}, cancellable=true)
    public void getMouseOver(float f, CallbackInfo callbackInfo) {
        Entity entity = this.field_78531_r.func_175606_aa();
        if (entity != null && this.field_78531_r.field_71441_e != null) {
            Vec3 vec3;
            this.field_78531_r.field_71424_I.func_76320_a("pick");
            this.field_78531_r.field_147125_j = null;
            Reach reach = Reach.getINSTANCE();
            double d = reach.isEnabled() ? (Double)reach.size.getValue() : (double)this.field_78531_r.field_71442_b.func_78757_d();
            this.field_78531_r.field_71476_x = entity.func_174822_a(reach.isEnabled() ? (Double)reach.size.getValue() : d, f);
            double d2 = d;
            Vec3 vec32 = entity.func_174824_e(f);
            boolean bl = false;
            if (this.field_78531_r.field_71442_b.func_78749_i()) {
                d = 6.0;
                d2 = 6.0;
            } else if (d > 3.0) {
                bl = true;
            }
            if (this.field_78531_r.field_71476_x != null) {
                d2 = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(vec32);
            }
            if (reach.isEnabled() && (vec3 = entity.func_174822_a(d2 = ((Double)reach.size.getValue()).doubleValue(), f)) != null) {
                d2 = vec3.field_72307_f.func_72438_d(vec32);
            }
            vec3 = entity.func_70676_i(f);
            Vec3 vec33 = vec32.func_72441_c(vec3.field_72450_a * d, vec3.field_72448_b * d, vec3.field_72449_c * d);
            this.field_78528_u = null;
            Vec3 vec34 = null;
            float f2 = 1.0f;
            List list = this.field_78531_r.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec3.field_72450_a * d, vec3.field_72448_b * d, vec3.field_72449_c * d).func_72314_b((double)f2, (double)f2, (double)f2), Predicates.and((Predicate)EntitySelectors.field_180132_d, MixinEntityRenderer::lambda$getMouseOver$0));
            double d3 = d2;
            for (int i = 0; i < list.size(); ++i) {
                double d4;
                Entity entity2 = (Entity)list.get(i);
                float f3 = entity2.func_70111_Y();
                AxisAlignedBB axisAlignedBB = entity2.func_174813_aQ().func_72314_b((double)f3, (double)f3, (double)f3);
                MovingObjectPosition movingObjectPosition = axisAlignedBB.func_72327_a(vec32, vec33);
                if (axisAlignedBB.func_72318_a(vec32)) {
                    if (!(d3 >= 0.0)) continue;
                    this.field_78528_u = entity2;
                    vec34 = movingObjectPosition == null ? vec32 : movingObjectPosition.field_72307_f;
                    d3 = 0.0;
                    continue;
                }
                if (movingObjectPosition == null || !((d4 = vec32.func_72438_d(movingObjectPosition.field_72307_f)) < d3) && d3 != 0.0) continue;
                if (entity2 == entity.field_70154_o && !entity.canRiderInteract()) {
                    if (d3 != 0.0) continue;
                    this.field_78528_u = entity2;
                    vec34 = movingObjectPosition.field_72307_f;
                    continue;
                }
                this.field_78528_u = entity2;
                vec34 = movingObjectPosition.field_72307_f;
                d3 = d4;
            }
            if (this.field_78528_u != null && bl) {
                double d5 = vec32.func_72438_d(vec34);
                double d6 = reach.isEnabled() ? (Double)reach.size.getValue() : 3.0;
                if (d5 > d6) {
                    this.field_78528_u = null;
                    this.field_78531_r.field_71476_x = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec34, (EnumFacing)null, new BlockPos(vec34));
                }
            }
            if (this.field_78528_u != null && (d3 < d2 || this.field_78531_r.field_71476_x == null)) {
                this.field_78531_r.field_71476_x = new MovingObjectPosition(this.field_78528_u, vec34);
                if (this.field_78528_u instanceof EntityLivingBase || this.field_78528_u instanceof EntityItemFrame) {
                    this.field_78531_r.field_147125_j = this.field_78528_u;
                }
            }
            this.field_78531_r.field_71424_I.func_76319_b();
        }
        callbackInfo.cancel();
    }

    private static boolean lambda$getMouseOver$0(Entity entity) {
        return entity.func_70067_L();
    }
}

