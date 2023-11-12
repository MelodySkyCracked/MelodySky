/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Mining;

import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.QOL.FrozenTreasureESP;

public class IceNuker
extends Module {
    private ArrayList broken = new ArrayList();
    private BlockPos closestIce;
    private int ticks = 0;
    private TimerUtil timer = new TimerUtil();
    private Numbers range = new Numbers("Range", 3.8, 2.0, 6.0, 0.1);
    private Numbers depth = new Numbers("Depth", 1.0, 0.0, 4.0, 0.5);
    private Numbers bps = new Numbers("Block Per Sec.", 10.0, 1.0, 50.0, 1.0);
    private Option fto = new Option("Treasure Only", false);

    public IceNuker() {
        super("IceNuker", new String[]{""}, ModuleType.Macros);
        this.addValues(this.bps, this.depth, this.range, this.fto);
        this.setModInfo("Auto Fuck Ice");
    }

    @Override
    public void onDisable() {
        this.broken.clear();
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        if (this.broken.size() > 10) {
            this.broken.clear();
        }
        if (this.ticks > 10) {
            this.broken.clear();
            this.ticks = 0;
        }
        if (!this.timer.hasReached(1000 / ((Double)this.bps.getValue()).intValue())) {
            return;
        }
        this.closestIce = this.closestIce();
        if (this.closestIce != null) {
            MovingObjectPosition movingObjectPosition = this.mc.field_71476_x;
            movingObjectPosition.field_72307_f = new Vec3((Vec3i)this.closestIce);
            EnumFacing enumFacing = movingObjectPosition.field_178784_b;
            if (enumFacing != null && this.mc.field_71439_g != null) {
                this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.closestIce, enumFacing));
            }
            this.mc.field_71439_g.func_71038_i();
            this.broken.add(this.closestIce);
        }
        ++this.ticks;
        this.timer.reset();
    }

    @EventHandler
    public void renderWorld(EventRender3D eventRender3D) {
        if (this.closestIce != null) {
            RenderUtil.drawSolidBlockESP(this.closestIce, Colors.MAGENTA.c, 3.5f, eventRender3D.getPartialTicks());
        }
    }

    private BlockPos closestIce() {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return null;
        }
        if (((Boolean)this.fto.getValue()).booleanValue()) {
            ArrayList arrayList = ((FrozenTreasureESP)Client.instance.getModuleManager().getModuleByClass(FrozenTreasureESP.class)).ices;
            arrayList.removeIf(this::lambda$closestIce$0);
            if (!arrayList.isEmpty()) {
                return (BlockPos)arrayList.get(0);
            }
            return null;
        }
        float f = ((Double)this.range.getValue()).floatValue();
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c().func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i((double)f, (double)f, (double)f);
        Vec3i vec3i2 = new Vec3i((double)f, ((Double)this.depth.getValue()).doubleValue(), (double)f);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (blockPos != null) {
            for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i2))) {
                IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2);
                if (iBlockState.func_177230_c() != Blocks.field_150432_aD || this.broken.contains(blockPos2)) continue;
                arrayList.add(blockPos2);
            }
        }
        if (!arrayList.isEmpty()) {
            return (BlockPos)arrayList.get(0);
        }
        return null;
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        KeyBinding.func_74506_a();
        this.setEnabled(false);
    }

    private boolean lambda$closestIce$0(BlockPos blockPos) {
        return this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) > (Double)this.range.getValue() || this.broken.contains(blockPos);
    }
}

