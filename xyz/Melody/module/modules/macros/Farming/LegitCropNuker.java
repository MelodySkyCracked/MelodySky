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
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Farming;

import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.math.MathUtil;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.others.AutoWalk;

public class LegitCropNuker
extends Module {
    private BlockPos blockPos = null;
    private TimerUtil timer = new TimerUtil();
    private final ArrayList broken = new ArrayList();
    private Option rqMouseDown = new Option("MouseDown", true);
    private Numbers bps = new Numbers("BPS", 20.0, 20.0, 80.0, 1.0);
    private Numbers range = new Numbers("Range", 4.0, 1.0, 6.0, 0.5);

    public LegitCropNuker() {
        super("LegitCropNuker", new String[0], ModuleType.Macros);
        this.addValues(this.bps, this.range, this.rqMouseDown);
        this.setModInfo("Breaking Crops More Faster.");
    }

    @EventHandler
    public void tick(EventRender2D eventRender2D) {
        IBlockState iBlockState;
        if (this.broken.size() > 20) {
            this.broken.clear();
        }
        if (!this.timer.hasReached(1000.0 / (Double)this.bps.getValue())) {
            return;
        }
        if (!(!this.mc.field_71474_y.field_74312_F.func_151470_d() && ((Boolean)this.rqMouseDown.getValue()).booleanValue() || this.blockPos == null || (iBlockState = this.mc.field_71441_e.func_180495_p(this.blockPos)).func_177230_c() != Blocks.field_150388_bm && iBlockState.func_177230_c() != Blocks.field_150469_bN && iBlockState.func_177230_c() != Blocks.field_150464_aj && iBlockState.func_177230_c() != Blocks.field_150459_bM && iBlockState.func_177230_c() != Blocks.field_150423_aK && iBlockState.func_177230_c() != Blocks.field_150440_ba && iBlockState.func_177230_c() != Blocks.field_150338_P && iBlockState.func_177230_c() != Blocks.field_150337_Q && iBlockState.func_177230_c() != Blocks.field_150375_by && iBlockState.func_177230_c() != Blocks.field_150434_aF && iBlockState.func_177230_c() != Blocks.field_150436_aH)) {
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
        this.blockPos = this.closestCrop(eventRender3D);
        if (this.blockPos != null) {
            RenderUtil.drawSolidBlockESP(this.blockPos, Colors.SLOWLY3.c, eventRender3D.getPartialTicks());
        }
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        if (((Boolean)this.rqMouseDown.getValue()).booleanValue()) {
            return;
        }
        AutoWalk autoWalk = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
        if (autoWalk.activeWorldChange) {
            return;
        }
        Helper.sendMessage("[MacroProtection] Auto Disabled " + EnumChatFormatting.GREEN + this.getName() + EnumChatFormatting.GRAY + " due to World Change.");
        this.setEnabled(false);
    }

    /*
     * Exception decompiling
     */
    private BlockPos closestCrop(EventRender3D var1) {
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

    private Vec3 scaleVec(Vec3 vec3, float f) {
        return new Vec3(vec3.field_72450_a * (double)f, vec3.field_72448_b * (double)f, vec3.field_72449_c * (double)f);
    }

    private static double lambda$closestCrop$0(Vec3 vec3, Vec3 vec32) {
        return MathUtil.distanceToPos(new BlockPos(vec3), new BlockPos(vec32));
    }
}

