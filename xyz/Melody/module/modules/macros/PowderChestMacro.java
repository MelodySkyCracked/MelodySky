/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.S2APacketParticles
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.module.modules.macros;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.math.RotationUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class PowderChestMacro
extends Module {
    public static Vec3d nextRotation = null;
    public static ArrayList done = new ArrayList();
    private TimerUtil rightClickerTimer = new TimerUtil();
    public Option instant = new Option("Instant", false);
    public Option silent = new Option("Silent", false);
    public static Vec3d chest;
    public static BlockPos chestPos;
    private float silentYaw;
    private float silentPitch;
    private int ticks = 0;

    public PowderChestMacro() {
        super("PowderChest", new String[]{"chest"}, ModuleType.Macros);
        this.addValues(this.instant, this.silent);
        this.setModInfo("Auto Unlock Powder Chests.");
    }

    @EventHandler
    private void onUpdate(EventTick eventTick) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            return;
        }
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        chest = this.getChest();
        chestPos = this.getChestPos();
        if (((Boolean)this.instant.getValue()).booleanValue()) {
            nextRotation = chest;
        }
        this.ticks = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventHandler
    private void onRotation(EventPreUpdate eventPreUpdate) {
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            return;
        }
        if (chest != null) {
            if (chestPos != null) {
                if (nextRotation != null) {
                    float f = RotationUtil.vec3ToRotation(nextRotation).getYaw();
                    float f2 = RotationUtil.vec3ToRotation(nextRotation).getPitch();
                    float f3 = 300.0f;
                    if (((Boolean)this.silent.getValue()).booleanValue()) {
                        this.silentYaw = this.smoothRotation(this.silentYaw, f, f3);
                        eventPreUpdate.setYaw(this.silentYaw);
                        this.silentPitch = this.smoothRotation(this.silentPitch, f2, f3);
                        eventPreUpdate.setPitch(this.silentPitch);
                    } else {
                        this.mc.field_71439_g.field_70177_z = this.smoothRotation(this.mc.field_71439_g.field_70177_z, f, f3);
                        this.mc.field_71439_g.field_70125_A = this.smoothRotation(this.mc.field_71439_g.field_70125_A, f2, f3);
                    }
                    double d = RotationUtil.fixRadius((Boolean)this.silent.getValue() != false ? this.silentYaw : this.mc.field_71439_g.field_70177_z);
                    double d2 = RotationUtil.fixRadius((Boolean)this.silent.getValue() != false ? this.silentPitch : this.mc.field_71439_g.field_70125_A);
                    double d3 = Math.abs(d - (double)RotationUtil.fixRadius(f));
                    double d4 = Math.abs(d2 - (double)RotationUtil.fixRadius(f2));
                    if ((Boolean)this.instant.getValue() == false) return;
                    if (!(d3 < 5.0)) return;
                    if (!(d4 < 5.0)) return;
                    if (!this.rightClickerTimer.hasReached(330.0)) return;
                    this.mc.field_71442_b.func_178890_a(this.mc.field_71439_g, this.mc.field_71441_e, this.mc.field_71439_g.func_70694_bm(), chestPos, RotationUtil.getClosestEnum(chestPos), chest.toVec3());
                    this.mc.field_71439_g.func_71038_i();
                    done.add(chestPos);
                    this.rightClickerTimer.reset();
                    return;
                }
            }
        }
        this.silentYaw = this.mc.field_71439_g.field_70177_z;
        this.silentPitch = this.mc.field_71439_g.field_70125_A;
    }

    @EventHandler
    public void receivePacket(EventPacketRecieve eventPacketRecieve) {
        S2APacketParticles s2APacketParticles;
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows) || ((Boolean)this.instant.getValue()).booleanValue()) {
            return;
        }
        if (eventPacketRecieve.getPacket() instanceof S2APacketParticles && (s2APacketParticles = (S2APacketParticles)eventPacketRecieve.getPacket()).func_179749_a().equals((Object)EnumParticleTypes.CRIT)) {
            double d;
            Vec3 vec3 = new Vec3(s2APacketParticles.func_149220_d(), s2APacketParticles.func_149226_e(), s2APacketParticles.func_149225_f());
            if (chest != null && (d = chest.distanceTo(Vec3d.of(vec3))) < 1.0) {
                nextRotation = Vec3d.of(vec3);
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        done.clear();
        super.onDisable();
    }

    @EventHandler
    public void onR3D(EventRender3D eventRender3D) {
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            return;
        }
        for (TileEntity tileEntity : this.mc.field_71441_e.field_147482_g) {
            if (!(tileEntity instanceof TileEntityChest)) continue;
            TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
            RenderUtil.drawSolidBlockESP(tileEntityChest.func_174877_v(), Colors.BLUE.c, eventRender3D.getPartialTicks());
        }
        if (this.getChestPos() != null) {
            RenderUtil.drawSolidBlockESP(this.getChestPos(), Colors.ORANGE.c, eventRender3D.getPartialTicks());
        }
    }

    private Vec3d getChest() {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return null;
        }
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        if (!arrayList.isEmpty()) {
            arrayList.clear();
        }
        for (TileEntity tileEntity : this.mc.field_71441_e.field_147482_g) {
            if (!(tileEntity instanceof TileEntityChest)) continue;
            TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
            Vec3d vec3d = Vec3d.ofCenter((Vec3i)tileEntityChest.func_174877_v());
            BlockPos blockPos = tileEntityChest.func_174877_v();
            if (done.contains(blockPos) || !(vec3d.distanceTo(Vec3d.ofCenter((Vec3i)this.mc.field_71439_g.func_180425_c().func_177984_a())) < 4.0)) continue;
            arrayList.add(vec3d);
        }
        arrayList.sort(Comparator.comparingDouble(this::lambda$getChest$0));
        if (!arrayList.isEmpty()) {
            return (Vec3d)arrayList.get(0);
        }
        return null;
    }

    private BlockPos getChestPos() {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return null;
        }
        BlockPos blockPos = null;
        if (chest != null) {
            blockPos = new BlockPos(chest.toVec3());
        }
        return blockPos;
    }

    private float smoothRotation(float f, float f2, float f3) {
        float f4 = MathHelper.func_76142_g((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return RotationUtil.fixRadius(f + f4 / 2.0f);
    }

    private double lambda$getChest$0(Vec3d vec3d) {
        return vec3d.distanceTo(Vec3d.ofCenter((Vec3i)this.mc.field_71439_g.func_180425_c().func_177984_a()));
    }
}

