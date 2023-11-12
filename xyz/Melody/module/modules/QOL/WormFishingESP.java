/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class WormFishingESP
extends Module {
    private Option debug = new Option("Debug", false);
    private ArrayList blockPoss = new ArrayList();
    private Thread thread;
    private int ticks = 0;

    public WormFishingESP() {
        super("WormFishingESP", new String[]{"wfe"}, ModuleType.QOL);
        this.setEnabled(false);
        this.addValues(this.debug);
        this.setModInfo("Worm Fishing Lava ESP.");
    }

    @Override
    public void onDisable() {
        this.thread = null;
        this.blockPoss.clear();
        this.thread = null;
        super.onDisable();
    }

    @EventHandler
    private void onUpdate(EventTick eventTick) {
        if (this.ticks < 10) {
            ++this.ticks;
            return;
        }
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            this.thread = null;
            this.blockPoss.clear();
            return;
        }
        if (this.mc.field_71441_e != null && this.mc.field_71439_g != null && this.thread == null) {
            this.thread = new Thread(this::lambda$onUpdate$0, "MelodySky -> LavaScanner");
            this.thread.start();
        }
        this.ticks = 0;
    }

    @EventHandler
    private void on3D(EventRender3D eventRender3D) {
        BlockPos blockPos;
        int n;
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            return;
        }
        for (n = 0; n < this.blockPoss.size(); ++n) {
            blockPos = (BlockPos)this.blockPoss.get(n);
            if (!(this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) > 250.0)) continue;
            this.blockPoss.remove(blockPos);
            break;
        }
        for (n = 0; n < this.blockPoss.size(); ++n) {
            blockPos = (BlockPos)this.blockPoss.get(n);
            Color color = new Color(255, 110, 0, 200);
            double d = this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p());
            if (d < 5.0) continue;
            if (d < 20.0) {
                color = ColorUtils.addAlpha(color, (int)(d * 10.0));
            }
            RenderUtil.drawSolidBlockESP(blockPos, color.getRGB(), eventRender3D.getPartialTicks());
        }
    }

    private void updateBlocks() {
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        for (BlockPos blockPos : BlockPos.func_177980_a((BlockPos)new BlockPos(473, 150, 473), (BlockPos)new BlockPos(823, 64, 823))) {
            if (this.mc.field_71441_e == null) break;
            IBlockState iBlockState = this.mc.field_71441_e.func_180495_p(blockPos);
            if (!this.isEnabled() || !this.thread.isAlive()) {
                this.blockPoss.clear();
                return;
            }
            if (this.mc.field_71439_g.func_70011_f((double)blockPos.func_177958_n(), (double)blockPos.func_177956_o(), (double)blockPos.func_177952_p()) > 250.0 || iBlockState.func_177230_c() != Blocks.field_150353_l || this.blockPoss.contains(blockPos)) continue;
            if (((Boolean)this.debug.getValue()).booleanValue()) {
                Helper.sendMessage(blockPos);
            }
            this.blockPoss.add(blockPos);
        }
    }

    private void lambda$onUpdate$0() {
        while (this.isEnabled() && this.mc.field_71441_e != null) {
            this.updateBlocks();
        }
    }
}

