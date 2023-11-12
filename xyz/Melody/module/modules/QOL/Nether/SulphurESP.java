/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.Vec3i
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Nether;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class SulphurESP
extends Module {
    private Numbers range = new Numbers("Range", 200.0, 100.0, 500.0, 10.0);
    private Option debug = new Option("Debug", false);
    private ArrayList blockPoss = new ArrayList();
    private Thread thread;
    private int ticks = 0;

    public SulphurESP() {
        super("SulphurESP", new String[]{"sulesp"}, ModuleType.Nether);
        this.addValues(this.range, this.debug);
        this.setModInfo("Sulphur ESP (Crimson Island).");
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        this.blockPoss.clear();
    }

    @Override
    public void onDisable() {
        this.thread = null;
        this.blockPoss.clear();
        super.onDisable();
    }

    @EventHandler
    private void onUpdate(EventTick eventTick) {
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crimson_Island) {
            this.thread = null;
            this.blockPoss.clear();
            return;
        }
        if (this.mc.field_71441_e != null && this.mc.field_71439_g != null && this.thread == null) {
            this.thread = new Thread(this::lambda$onUpdate$0, "MelodySky-SulphurESP Thread");
            this.thread.start();
        }
        this.ticks = 0;
    }

    @EventHandler
    private void on3D(EventRender3D eventRender3D) {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crimson_Island) {
            return;
        }
        for (int i = 0; i < this.blockPoss.size(); ++i) {
            BlockPos blockPos = (BlockPos)this.blockPoss.get(i);
            Color color = new Color(Colors.YELLOW.c);
            int n = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
            RenderUtil.drawSolidBlockESP(blockPos, n, eventRender3D.getPartialTicks());
        }
    }

    @EventHandler
    private void onBlockChange(BlockChangeEvent blockChangeEvent) {
        if (this.blockPoss.contains(blockChangeEvent.getPosition())) {
            this.blockPoss.remove(blockChangeEvent.getPosition());
        }
    }

    private void updateBlocks() {
        if (Client.instance.sbArea.getCurrentArea() != Areas.Crimson_Island) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        Vec3i vec3i = new Vec3i(((Double)this.range.getValue()).doubleValue(), (double)((int)(this.mc.field_71439_g.field_70163_u - (this.mc.field_71439_g.field_70163_u - 32.0))), ((Double)this.range.getValue()).doubleValue());
        Vec3i vec3i2 = new Vec3i(((Double)this.range.getValue()).doubleValue(), this.mc.field_71439_g.field_70163_u - (this.mc.field_71439_g.field_70163_u - 200.0), ((Double)this.range.getValue()).doubleValue());
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)this.mc.field_71439_g.func_180425_c().func_177971_a(vec3i2), (BlockPos)this.mc.field_71439_g.func_180425_c().func_177973_b(vec3i))) {
            if (this.mc.field_71441_e == null) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled() || !this.thread.isAlive()) {
                this.blockPoss.clear();
                return;
            }
            if (this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) > 250.0 || iBlockState.func_177230_c() != Blocks.field_150360_v || this.blockPoss.contains(blockPos)) continue;
            if (((Boolean)this.debug.getValue()).booleanValue()) {
                Helper.sendMessage(blockPos);
            }
            this.blockPoss.add(blockPos);
        }
    }

    private void lambda$onUpdate$0() {
        while (this.isEnabled() && Client.instance.sbArea.getCurrentArea() == Areas.Crimson_Island && this.mc.field_71441_e != null) {
            this.updateBlocks();
        }
    }
}

