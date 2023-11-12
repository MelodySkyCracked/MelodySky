/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C02PacketUseEntity
 *  net.minecraft.network.play.client.C02PacketUseEntity$Action
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
 *  net.minecraft.network.play.client.C0APacketAnimation
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.module.balance;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPostUpdate;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.FadeUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.injection.mixins.entity.EPSPAccessor;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class Aura
extends Module {
    public EntityLivingBase curTarget;
    private List targets = new ArrayList();
    private int index;
    private Mode mode = new Mode("Mode", AuraMode.values(), AuraMode.Single);
    private Numbers cps = new Numbers("CPS", 10.0, 1.0, 20.0, 0.5);
    private Numbers range = new Numbers("Range", 4.5, 1.0, 6.0, 0.1);
    private Numbers fov = new Numbers("Attack FOV", 360.0, 1.0, 360.0, 10.0);
    private Numbers sinC = new Numbers("Single Switch", 200.0, 1.0, 1000.0, 1.0);
    public Option targetHud = new Option("TargetHUD", true);
    private Option noSwing = new Option("No Swing", false);
    private Option mouseDown = new Option("Mouse Down", false);
    private Option ksprint = new Option("Keep Sprint", true);
    private Option pre = new Option("PreAttack", false);
    private Option rot = new Option("Rotation", true);
    private Option esp = new Option("ESP", false);
    private Option players = new Option("Players", true);
    private Option friend = new Option("FriendFilter", true);
    private Option team = new Option("TeammateFilter", true);
    private Option animals = new Option("Animals", true);
    private Option mobs = new Option("Mobs", false);
    private Option invis = new Option("Invisibles", false);
    private Option death = new Option("DeathCheck", true);
    public boolean isBlocking;
    private Comparator angleComparator = Comparator.comparingDouble(this::lambda$new$0);
    private TimerUtil attackTimer = new TimerUtil();
    private TimerUtil switchTimer = new TimerUtil();
    private TimerUtil singleTimer = new TimerUtil();
    private TimerUtil sinCTimer = new TimerUtil();
    private boolean cpsReady = false;
    private static Aura INSTANCE;

    public Aura() {
        super("KillAura", new String[]{"ka", "aura", "killa"}, ModuleType.Balance);
        this.addValues(this.mode, this.cps, this.range, this.fov, this.sinC, this.targetHud, this.noSwing, this.mouseDown, this.ksprint, this.pre, this.rot, this.esp, this.players, this.friend, this.team, this.animals, this.mobs, this.invis, this.death);
    }

    public static Aura getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (Aura)Client.instance.getModuleManager().getModuleByClass(Aura.class);
        }
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        this.curTarget = null;
        this.targets.clear();
        if (this.isBlocking) {
            this.unBlock();
        }
    }

    @Override
    public void onEnable() {
        this.curTarget = null;
        this.index = 0;
    }

    public double random(double d, double d2) {
        Random random = new Random();
        return d + (double)((int)(random.nextDouble() * (d2 - d)));
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        if (this.curTarget == null) {
            return;
        }
        if (((Boolean)this.mouseDown.getValue()).booleanValue() && !this.mc.field_71474_y.field_74312_F.func_151470_d()) {
            return;
        }
        if (((Boolean)this.esp.getValue()).booleanValue()) {
            if (this.mode.getValue() == AuraMode.Multi) {
                for (Entity entity : this.targets) {
                    Color color = new Color(60, 127, 130, 120);
                    RenderUtil.drawFilledESP(entity, color, eventRender3D, 3.0f);
                }
                Color color = new Color(200, 30, 30, 190);
                RenderUtil.drawFilledESP((Entity)this.curTarget, color, eventRender3D, 6.0f);
                return;
            }
            float f = eventRender3D.getPartialTicks();
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            RenderUtil.startDrawing();
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)4.0f);
            GL11.glBegin((int)3);
            double d = this.curTarget.field_70142_S + (this.curTarget.field_70165_t - this.curTarget.field_70142_S) * (double)f - this.mc.func_175598_ae().field_78730_l;
            double d2 = this.curTarget.field_70137_T + (this.curTarget.field_70163_u - this.curTarget.field_70137_T) * (double)f - this.mc.func_175598_ae().field_78731_m;
            double d3 = this.curTarget.field_70136_U + (this.curTarget.field_70161_v - this.curTarget.field_70136_U) * (double)f - this.mc.func_175598_ae().field_78728_n;
            for (int i = 0; i <= 10; ++i) {
                RenderUtil.glColor(FadeUtil.fade(FadeUtil.BLUE.getColor()).getRGB());
                GL11.glVertex3d((double)(d + 1.1 * Math.cos((double)i * (Math.PI * 2) / 9.0)), (double)d2, (double)(d3 + 1.1 * Math.sin((double)i * (Math.PI * 2) / 9.0)));
            }
            GL11.glEnd();
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            RenderUtil.stopDrawing();
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
        }
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (!((Boolean)this.ksprint.getValue()).booleanValue()) {
            this.mc.field_71439_g.func_70031_b(false);
        }
        if (((Boolean)this.death.getValue()).booleanValue() && this.mc.field_71439_g != null) {
            if (!this.mc.field_71439_g.func_70089_S() || this.mc.field_71462_r != null && this.mc.field_71462_r instanceof GuiGameOver) {
                this.setEnabled(false);
                return;
            }
            if (this.mc.field_71439_g.field_70173_aa <= 1) {
                this.setEnabled(false);
                return;
            }
        }
    }

    private void block() {
        if (this != null) {
            ((EPSPAccessor)this.mc.field_71439_g).setItemInUseCount(this.mc.field_71439_g.func_70694_bm().func_77988_m());
            this.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a((Packet)new C08PacketPlayerBlockPlacement(this.mc.field_71439_g.field_71071_by.func_70448_g()));
            this.isBlocking = true;
        }
    }

    private void unBlock() {
        if (this != null && this.isBlocking) {
            if (!this.mc.field_71439_g.func_70632_aY() && this.mc.field_71439_g.func_71052_bv() > 0) {
                ((EPSPAccessor)this.mc.field_71439_g).setItemInUseCount(0);
            }
            this.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, this.mc.field_71439_g.field_70701_bs != 0.0f || this.mc.field_71439_g.field_70702_br != 0.0f ? new BlockPos(-1, -1, -1) : BlockPos.field_177992_a, EnumFacing.DOWN));
            this.isBlocking = false;
        }
    }

    @EventHandler
    private void onAuraLoad(EventRender2D eventRender2D) {
        if (this.curTarget != null && (!this.curTarget.func_70089_S() || this.curTarget.func_70662_br())) {
            EntityLivingBase entityLivingBase = this.curTarget = this.getTargets((Double)this.range.getValue()).isEmpty() ? null : (EntityLivingBase)this.getTargets((Double)this.range.getValue()).get(0);
        }
        if (this.attackTimer.hasReached(1000.0 / ((Double)this.cps.getValue() + this.random(3.0, 3.0)))) {
            this.cpsReady = true;
        }
        this.targets.removeIf(this::lambda$onAuraLoad$1);
        if (this.targets.size() > 0 && this.mode.getValue() == AuraMode.Multi) {
            if (this.curTarget == null) {
                this.curTarget = (EntityLivingBase)this.targets.get(0);
            }
            if (this.curTarget.field_70737_aN > 0 || this.switchTimer.hasReached((Double)this.sinC.getValue())) {
                this.curTarget.field_70737_aN = 0;
                ++this.index;
                if (this.index + 1 > this.targets.size()) {
                    this.index = 0;
                }
                this.curTarget = (EntityLivingBase)this.targets.get(this.index);
                this.switchTimer.reset();
            }
        }
    }

    @EventHandler
    private void tickAura(EventTick eventTick) {
        if (((Boolean)this.mouseDown.getValue()).booleanValue() && !this.mc.field_71474_y.field_74312_F.func_151470_d() && this.sinCTimer.hasReached(200.0)) {
            this.curTarget = null;
            if (this.isBlocking) {
                this.unBlock();
            }
            this.sinCTimer.reset();
            return;
        }
        if (this.mode.getValue() == AuraMode.Single && (this.curTarget == null || this.curTarget.field_70128_L || !this.curTarget.func_70089_S() || (double)this.mc.field_71439_g.func_70032_d((Entity)this.curTarget) > (Double)this.range.getValue() || RotationUtil.isInFov(this.mc.field_71439_g.field_70177_z, this.mc.field_71439_g.field_70125_A, this.curTarget.field_70165_t, this.curTarget.field_70163_u, this.curTarget.field_70161_v) > (Double)this.fov.getValue())) {
            this.curTarget = null;
            this.targets = this.getTargets((Double)this.range.getValue());
            this.targets.sort(this.angleComparator);
        }
        if (this.curTarget != null && (this.curTarget.func_110143_aJ() == 0.0f || !this.curTarget.func_70089_S() || (double)this.mc.field_71439_g.func_70032_d((Entity)this.curTarget) > (Double)this.range.getValue())) {
            this.curTarget = null;
            ++this.index;
        }
    }

    @EventHandler
    private void onUpdate(EventPreUpdate eventPreUpdate) {
        if (!((Boolean)this.mouseDown.getValue()).booleanValue() || ((Boolean)this.mouseDown.getValue()).booleanValue() && this.mc.field_71474_y.field_74312_F.func_151470_d()) {
            boolean bl;
            if (this.mode.getValue() == AuraMode.Single && this.curTarget == null && !this.targets.isEmpty()) {
                this.curTarget = (EntityLivingBase)this.targets.get(0);
            }
            boolean bl2 = bl = Mouse.isButtonDown((int)1) && this.mc.field_71462_r == null && this.mc.field_71439_g.func_70694_bm() != null && this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
            if (this.curTarget == null && bl && this.isBlocking && this != null) {
                this.unBlock();
            }
            if (this != null && this.curTarget != null && bl && !this.isBlocking) {
                this.block();
            }
            if (this.mode.getValue() == AuraMode.Multi) {
                if (this.targets.isEmpty() || this.curTarget == null || this.singleTimer.hasReached((Double)this.sinC.getValue())) {
                    this.targets = this.getTargets((Double)this.range.getValue());
                }
                for (Entity entity : this.targets) {
                    if (entity != null && entity.func_70089_S() && !entity.field_70128_L && !((double)this.mc.field_71439_g.func_70032_d(entity) > (Double)this.range.getValue())) continue;
                    this.targets = this.getTargets((Double)this.range.getValue());
                }
                this.sinCTimer.reset();
            }
            if (this.targets.size() > 1 && this.mode.getValue() == AuraMode.Single && this.curTarget != null) {
                if ((double)this.curTarget.func_70032_d((Entity)this.mc.field_71439_g) > (Double)this.range.getValue()) {
                    this.curTarget = null;
                } else if (this.curTarget.field_70128_L) {
                    this.curTarget = null;
                }
                this.singleTimer.reset();
            }
            if (((Boolean)this.pre.getValue()).booleanValue() && this.curTarget != null) {
                if (((Boolean)this.pre.getValue()).booleanValue() && this.cpsReady) {
                    if (this != null && this.mc.field_71439_g.func_70632_aY() && this == this.curTarget) {
                        this.unBlock();
                    }
                    this.attack();
                    if (!this.mc.field_71439_g.func_70632_aY() && this != null && bl) {
                        this.block();
                    }
                }
                this.curTarget = null;
            }
        }
        if (!this.targets.isEmpty() && this.curTarget != null && this.mode.getValue() != AuraMode.Multi && ((Boolean)this.rot.getValue()).booleanValue()) {
            float f = this.getRotationFormEntity(this.curTarget)[0];
            float f2 = this.getRotationFormEntity(this.curTarget)[1];
            this.mc.field_71439_g.field_70759_as = f;
            Client.instance.rotationPitchHead = f2;
            this.mc.field_71439_g.field_70761_aq = f;
            this.mc.field_71439_g.field_70760_ar = f;
            eventPreUpdate.setYaw(f);
            eventPreUpdate.setPitch(f2);
        }
    }

    public float[] getRotationFormEntity(EntityLivingBase entityLivingBase) {
        return RotationUtil.getPredictedRotations(entityLivingBase);
    }

    @EventHandler
    private void onUpdatePost(EventPostUpdate eventPostUpdate) {
        if (((Boolean)this.mouseDown.getValue()).booleanValue() && !this.mc.field_71474_y.field_74312_F.func_151470_d()) {
            return;
        }
        if (((Boolean)this.pre.getValue()).booleanValue()) {
            return;
        }
        if (this.curTarget != null) {
            boolean bl;
            if (this.cpsReady) {
                if (this != null && this.mc.field_71439_g.func_70632_aY() && this == this.curTarget) {
                    this.unBlock();
                }
                this.attack();
            }
            boolean bl2 = bl = Mouse.isButtonDown((int)1) && this.mc.field_71462_r == null && this.mc.field_71439_g.func_70694_bm() != null && this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
            if (!this.mc.field_71439_g.func_70632_aY() && this != null && bl) {
                this.block();
            }
        }
    }

    private void attack() {
        boolean bl;
        boolean bl2 = bl = Mouse.isButtonDown((int)1) && this.mc.field_71462_r == null && this.mc.field_71439_g.func_70694_bm() != null && this.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
        if (((Boolean)this.noSwing.getValue()).booleanValue() && bl) {
            this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C0APacketAnimation());
        } else {
            this.mc.field_71439_g.func_71038_i();
        }
        this.mc.field_71439_g.func_71047_c((Entity)this.curTarget);
        this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C02PacketUseEntity((Entity)this.curTarget, C02PacketUseEntity.Action.ATTACK));
        if (this.cpsReady) {
            this.cpsReady = false;
            this.attackTimer.reset();
        }
        if (this.isBlocking && !bl) {
            this.unBlock();
        }
    }

    public List getTargets(Double d) {
        if (this.mode.getValue() != AuraMode.Multi) {
            this.mc.field_71441_e.field_72996_f.sort(Comparator.comparingDouble(this::lambda$getTargets$2));
            return this.mc.field_71441_e.field_72996_f.subList(0, this.mc.field_71441_e.field_72996_f.size() > 4 ? 4 : this.mc.field_71441_e.field_72996_f.size()).stream().filter(this::lambda$getTargets$3).collect(Collectors.toList());
        }
        return this.mc.field_71441_e.field_72996_f.stream().filter(arg_0 -> this.lambda$getTargets$4(d, arg_0)).collect(Collectors.toList());
    }

    private boolean lambda$getTargets$4(Double d, Entity entity) {
        return (double)this.mc.field_71439_g.func_70032_d(entity) <= d && this == entity;
    }

    private boolean lambda$getTargets$3(Entity entity) {
        return this.isValidEntity(entity);
    }

    private double lambda$getTargets$2(Entity entity) {
        return this.mc.field_71439_g.func_70032_d(entity);
    }

    private boolean lambda$onAuraLoad$1(Entity entity) {
        return (double)this.mc.field_71439_g.func_70032_d(entity) > (Double)this.range.getValue() || !entity.func_70089_S() || entity.field_70128_L;
    }

    private double lambda$new$0(Entity entity) {
        return entity.func_70032_d((Entity)this.mc.field_71439_g);
    }

    static enum rotationMode {
        Tick,
        Packet;

    }

    static enum AuraMode {
        Multi,
        Single;

    }
}

