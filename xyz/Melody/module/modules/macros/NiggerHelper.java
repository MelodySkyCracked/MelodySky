/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemHoe
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
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
package xyz.Melody.module.modules.macros;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
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
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class NiggerHelper
extends Module {
    private ArrayList broken = new ArrayList();
    private BlockPos closestNigger;
    private int ticks = 0;
    private TimerUtil timer = new TimerUtil();
    private Numbers range = new Numbers("Range", 3.8, 2.0, 6.0, 0.1);

    public NiggerHelper() {
        super("PlotCleaner", new String[]{""}, ModuleType.Macros);
        this.addValues(this.range);
        this.setModInfo("Help You Fuck Plots in Garden.");
    }

    @Override
    public void onDisable() {
        this.broken.clear();
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventRender2D eventRender2D) {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Garden) {
            Helper.sendMessage("[Nigger Cleaner] This Feature Only Available in Garden.");
            this.setEnabled(false);
            return;
        }
        if (this.broken.size() > 10 || this.ticks > Minecraft.func_175610_ah()) {
            this.broken.clear();
            this.ticks = 0;
        }
        this.closestNigger = this.closestStone();
        if (this.closestNigger == null) {
            return;
        }
        Block block = this.mc.field_71441_e.func_180495_p(this.closestNigger).func_177230_c();
        if (block == Blocks.field_150364_r || block == Blocks.field_150485_bF || block == Blocks.field_150376_bx || block == Blocks.field_150373_bw || block == Blocks.field_150344_f) {
            this.swapToAxe();
        } else if (block == Blocks.field_150348_b) {
            this.swapToPickAxe();
        } else {
            this.swapToHoe();
        }
        if (!this.timer.hasReached(33.0)) {
            return;
        }
        if (this.closestNigger != null) {
            MovingObjectPosition movingObjectPosition = this.mc.field_71476_x;
            movingObjectPosition.field_72307_f = new Vec3((Vec3i)this.closestNigger);
            EnumFacing enumFacing = movingObjectPosition.field_178784_b;
            if (enumFacing != null && this.mc.field_71439_g != null) {
                this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.closestNigger, enumFacing));
            }
            this.mc.field_71439_g.func_71038_i();
            this.broken.add(this.closestNigger);
        }
        ++this.ticks;
        this.timer.reset();
    }

    @EventHandler
    public void renderWorld(EventRender3D eventRender3D) {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Garden) {
            return;
        }
        if (this.closestNigger != null) {
            RenderUtil.drawSolidBlockESP(this.closestNigger, Colors.ORANGE.c, 3.5f, eventRender3D.getPartialTicks());
        }
    }

    private BlockPos closestStone() {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return null;
        }
        float f = ((Double)this.range.getValue()).floatValue();
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c().func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i((double)f, 4.0, (double)f);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        if (blockPos != null) {
            for (BlockPos blockPos2 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i))) {
                IBlockState iBlockState;
                if (this.broken.contains(blockPos2) || (iBlockState = this.mc.field_71441_e.func_180495_p(blockPos2)).func_177230_c() != Blocks.field_150348_b && iBlockState.func_177230_c() != Blocks.field_150328_O && iBlockState.func_177230_c() != Blocks.field_150327_N && iBlockState.func_177230_c() != Blocks.field_150329_H && iBlockState.func_177230_c() != Blocks.field_150364_r && iBlockState.func_177230_c() != Blocks.field_150362_t && iBlockState.func_177230_c() != Blocks.field_150398_cm && iBlockState.func_177230_c() != Blocks.field_150485_bF && iBlockState.func_177230_c() != Blocks.field_150376_bx && iBlockState.func_177230_c() != Blocks.field_150373_bw && iBlockState.func_177230_c() != Blocks.field_150344_f || this.broken.contains(blockPos2)) continue;
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

    private void swapToHoe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemHoe)) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
        }
    }

    private void swapToAxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemAxe)) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
        }
    }

    private void swapToPickAxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = this.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (itemStack == null || itemStack.func_77973_b() == null || !(itemStack.func_77973_b() instanceof ItemPickaxe)) continue;
            this.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
        }
    }
}

