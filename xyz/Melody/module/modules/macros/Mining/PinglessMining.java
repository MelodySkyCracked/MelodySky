/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraft.util.Vec3
 */
package xyz.Melody.module.modules.macros.Mining;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class PinglessMining
extends Module {
    private BlockPos blockPos = null;
    private TimerUtil timer = new TimerUtil();
    private final ArrayList broken = new ArrayList();
    private KeyBinding left;
    private int ticks;

    public PinglessMining() {
        super("PinglessMining", new String[0], ModuleType.Macros);
        this.left = this.mc.field_71474_y.field_74312_F;
        this.ticks = 0;
    }

    @EventHandler
    public void tick(EventTick eventTick) {
        Block block;
        MovingObjectPosition movingObjectPosition;
        ++this.ticks;
        if (this.ticks % 40 == 0) {
            this.broken.clear();
        }
        if (!this.timer.hasReached(50.0)) {
            return;
        }
        if (this.left != null && this.left.func_151470_d() && this.blockPos != null && (movingObjectPosition = this.mc.field_71476_x) != null && movingObjectPosition.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && ((block = this.mc.field_71441_e.func_180495_p(movingObjectPosition.func_178782_a()).func_177230_c()) == Blocks.field_150348_b || block == Blocks.field_150412_bA || block == Blocks.field_150369_x || block == Blocks.field_150450_ax || block == Blocks.field_150366_p || block == Blocks.field_150352_o || block == Blocks.field_150365_q || block == Blocks.field_150482_ag || block == Blocks.field_150388_bm || block == Blocks.field_150436_aH || block == Blocks.field_150469_bN || block == Blocks.field_150459_bM)) {
            this.broken.add(this.blockPos);
            this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, EnumFacing.DOWN));
            this.mc.field_71439_g.func_71038_i();
        }
        this.timer.reset();
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            this.broken.clear();
            return;
        }
        this.blockPos = this.closestBlock(eventRender3D);
        if (this.blockPos != null) {
            RenderUtil.drawSolidBlockESP(this.blockPos, Colors.RED.c, eventRender3D.getPartialTicks());
        }
    }

    /*
     * Exception decompiling
     */
    private BlockPos closestBlock(EventRender3D var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl37 : ALOAD - null : trying to set 1 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static Vec3 scaleVec(Vec3 vec3, float f) {
        return new Vec3(vec3.field_72450_a * (double)f, vec3.field_72448_b * (double)f, vec3.field_72449_c * (double)f);
    }
}

