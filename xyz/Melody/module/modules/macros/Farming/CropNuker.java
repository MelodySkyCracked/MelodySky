/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Farming;

import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3i;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.MouseUtils;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.ActionMacro;
import xyz.Melody.module.modules.others.AutoWalk;

public class CropNuker
extends Module {
    private BlockPos crop = null;
    private final ArrayList broken = new ArrayList();
    private TimerUtil timer = new TimerUtil();
    private Mode mode = new Mode("Mode", crops.values(), crops.Cane);
    private Numbers range = new Numbers("Range", 5.0, 1.0, 6.0, 0.1);
    private Numbers bps = new Numbers("BlockPerSec", 40.0, 20.0, 80.0, 5.0);
    private Option ugm = new Option("UngrabMouse", false);
    private Numbers hoeSlot = new Numbers("HoeSlot", 1.0, 1.0, 8.0, 1.0);
    private Option lockHoe = new Option("LockHoe", false);
    private Option rotationCheck = new Option("RotationCheck", true);
    private Numbers rotRange = new Numbers("RotationDiff", 0.01, 0.001, 180.0, 0.001);
    private Option yCheck = new Option("YCheck", true);
    private Numbers yRange = new Numbers("YRange", 5.0, 1.0, 100.0, 1.0);
    private Option disable = new Option("AutoDisable", true);
    private int ticks = 0;
    private float lastYaw = 0.0f;
    private float lastPitch = 0.0f;
    private double lastY = 0.0;
    private int delayTick = 0;

    public CropNuker() {
        super("CropNuker", new String[]{"gn"}, ModuleType.Macros);
        this.addValues(this.mode, this.range, this.bps, this.ugm, this.hoeSlot, this.lockHoe, this.rotationCheck, this.rotRange, this.yCheck, this.yRange);
        this.setModInfo("Auto Break Crops Around You.");
    }

    @EventHandler
    private void onLockHoe(EventTick eventTick) {
        if (!((Boolean)this.lockHoe.getValue()).booleanValue()) {
            return;
        }
        this.mc.field_71439_g.field_71071_by.field_70461_c = ((Double)this.hoeSlot.getValue()).intValue() - 1;
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onChatRCV(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (Client.instance.sbArea.getCurrentArea() == Areas.Garden && string.equals("A player has been removed from your game.")) {
            this.mc.field_71439_g.func_71165_d("/is");
        }
    }

    @EventHandler
    private void checks(EventTick eventTick) {
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        if (((Boolean)this.rotationCheck.getValue()).booleanValue() && ((double)Math.abs(this.mc.field_71439_g.field_70177_z - this.lastYaw) > 0.01 || (double)Math.abs(this.mc.field_71439_g.field_70125_A - this.lastPitch) > (Double)this.rotRange.getValue())) {
            WindowsNotification.show("MelodySky CropNuker", "Detected Rotation Change.", TrayIcon.MessageType.WARNING);
            if (((Boolean)this.disable.getValue()).booleanValue()) {
                if (Client.instance.getModuleManager().getModuleByClass(ActionMacro.class).isEnabled()) {
                    Client.instance.getModuleManager().getModuleByClass(ActionMacro.class).setEnabled(false);
                }
                this.setEnabled(false);
            }
        }
        if (((Boolean)this.yCheck.getValue()).booleanValue() && Math.abs(this.mc.field_71439_g.field_70163_u - this.lastY) > (Double)this.yRange.getValue()) {
            WindowsNotification.show("MelodySky CropNuker", "Detected Position Y Change.");
            if (((Boolean)this.disable.getValue()).booleanValue()) {
                if (Client.instance.getModuleManager().getModuleByClass(ActionMacro.class).isEnabled()) {
                    Client.instance.getModuleManager().getModuleByClass(ActionMacro.class).setEnabled(false);
                }
                this.setEnabled(false);
            }
        }
        this.ticks = 0;
    }

    @Override
    public void onEnable() {
        if (((Boolean)this.ugm.getValue()).booleanValue()) {
            MouseUtils.ungrabMouse();
        }
        this.lastYaw = this.mc.field_71439_g.field_70177_z;
        this.lastPitch = this.mc.field_71439_g.field_70125_A;
        this.lastY = this.mc.field_71439_g.field_70163_u;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (((Boolean)this.ugm.getValue()).booleanValue()) {
            MouseUtils.regrabMouse();
        }
        this.crop = null;
        this.broken.clear();
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventRender2D eventRender2D) {
        if (this.mc.field_71439_g == null) {
            this.broken.clear();
            return;
        }
        if (!this.timer.hasReached(1000 / ((Double)this.bps.getValue()).intValue())) {
            return;
        }
        if ((double)this.delayTick > (Double)this.bps.getValue()) {
            this.broken.removeIf(this::checkDistance);
            this.delayTick = 0;
        }
        this.crop = this.closestCrop();
        if (this.crop != null) {
            this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.crop, EnumFacing.DOWN));
            this.mc.field_71439_g.func_71038_i();
            ++this.delayTick;
            this.broken.add(this.crop);
        }
        this.timer.reset();
    }

    @EventHandler
    public void onTick(EventRender3D eventRender3D) {
        if (this.crop != null) {
            RenderUtil.drawSolidBlockESP(this.crop, Colors.MAGENTA.c, eventRender3D.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        AutoWalk autoWalk = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
        if (autoWalk.activeWorldChange) {
            return;
        }
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    private BlockPos closestCrop() {
        if (this.mc.field_71441_e == null) {
            return null;
        }
        double d = 6.0;
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c();
        blockPos = blockPos.func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i(d, 2.0, d);
        Vec3i vec3i2 = new Vec3i(d, 0.0, d);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (blockPos != null) {
            switch (((Enum)this.mode.getValue()).toString().toLowerCase()) {
                case "all": {
                    for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2);
                        if (iBlockState.func_177230_c() != Blocks.field_150388_bm && iBlockState.func_177230_c() != Blocks.field_150469_bN && iBlockState.func_177230_c() != Blocks.field_150464_aj && iBlockState.func_177230_c() != Blocks.field_150459_bM && iBlockState.func_177230_c() != Blocks.field_150423_aK && iBlockState.func_177230_c() != Blocks.field_150440_ba && iBlockState.func_177230_c() != Blocks.field_150338_P && iBlockState.func_177230_c() != Blocks.field_150337_Q && iBlockState.func_177230_c() != Blocks.field_150375_by && iBlockState.func_177230_c() != Blocks.field_150434_aF && iBlockState.func_177230_c() != Blocks.field_150436_aH || this.broken.contains(blockPos2)) continue;
                        arrayList.add(blockPos2);
                    }
                    break;
                }
                case "cane": {
                    for (BlockPos blockPos3 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i2), (BlockPos)blockPos.func_177973_b(vec3i2))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos3);
                        if (iBlockState.func_177230_c() != Blocks.field_150436_aH || this.broken.contains(blockPos3)) continue;
                        arrayList.add(blockPos3);
                    }
                    break;
                }
                case "cactus": {
                    for (BlockPos blockPos4 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i2), (BlockPos)blockPos.func_177973_b(vec3i2))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos4);
                        if (iBlockState.func_177230_c() != Blocks.field_150434_aF || this.broken.contains(blockPos4)) continue;
                        arrayList.add(blockPos4);
                    }
                    break;
                }
                case "netherwart": {
                    for (BlockPos blockPos5 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos5);
                        if (iBlockState.func_177230_c() != Blocks.field_150388_bm || this.broken.contains(blockPos5)) continue;
                        arrayList.add(blockPos5);
                    }
                    break;
                }
                case "wheat": {
                    for (BlockPos blockPos6 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos6);
                        if (iBlockState.func_177230_c() != Blocks.field_150464_aj || this.broken.contains(blockPos6)) continue;
                        arrayList.add(blockPos6);
                    }
                    break;
                }
                case "carrot": {
                    for (BlockPos blockPos7 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos7);
                        if (iBlockState.func_177230_c() != Blocks.field_150459_bM || this.broken.contains(blockPos7)) continue;
                        arrayList.add(blockPos7);
                    }
                    break;
                }
                case "potato": {
                    for (BlockPos blockPos8 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos8);
                        if (iBlockState.func_177230_c() != Blocks.field_150469_bN || this.broken.contains(blockPos8)) continue;
                        arrayList.add(blockPos8);
                    }
                    break;
                }
                case "pumpkin": {
                    for (BlockPos blockPos9 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos9);
                        if (iBlockState.func_177230_c() != Blocks.field_150423_aK || this.broken.contains(blockPos9)) continue;
                        arrayList.add(blockPos9);
                    }
                    break;
                }
                case "melon": {
                    for (BlockPos blockPos10 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos10);
                        if (iBlockState.func_177230_c() != Blocks.field_150440_ba || this.broken.contains(blockPos10)) continue;
                        arrayList.add(blockPos10);
                    }
                    break;
                }
                case "mushroom": {
                    for (BlockPos blockPos11 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos11);
                        if (iBlockState.func_177230_c() != Blocks.field_150338_P && iBlockState.func_177230_c() != Blocks.field_150337_Q || this.broken.contains(blockPos11)) continue;
                        arrayList.add(blockPos11);
                    }
                    break;
                }
                case "cocoa": {
                    for (BlockPos blockPos12 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                        IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos12);
                        if (iBlockState.func_177230_c() != Blocks.field_150375_by || this.broken.contains(blockPos12)) continue;
                        arrayList.add(blockPos12);
                    }
                    break;
                }
            }
        }
        arrayList.removeIf(this::checkDistance);
        arrayList.sort(Comparator.comparingDouble(this::lambda$closestCrop$0));
        if (!arrayList.isEmpty()) {
            return (BlockPos)arrayList.get(0);
        }
        return null;
    }

    private boolean checkDistance(BlockPos blockPos) {
        Vec3d vec3d = Vec3d.ofBottomCenter((Vec3i)blockPos);
        return (double)MathUtil.distanceToXYZ(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v, vec3d.getX(), vec3d.getY(), vec3d.getZ()) > (Double)this.range.getValue();
    }

    private double lambda$closestCrop$0(BlockPos blockPos) {
        return MathUtil.distanceToXYZ(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v, blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
    }

    private static enum crops {
        ALL,
        Cane,
        Cactus,
        NetherWart,
        Wheat,
        Carrot,
        Potato,
        Pumpkin,
        Melon,
        Mushroom,
        Cocoa;

    }
}

