/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C07PacketPlayerDigging$Action
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.Vec3i
 */
package xyz.Melody.module.modules.macros.Mining;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Vec3d;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Mining.AutoRuby;

public class RouteHelper
extends Module {
    private BlockPos blockPos = null;
    private ArrayList routeVecs = new ArrayList();
    private ArrayList routes = new ArrayList();
    private ArrayList broken = new ArrayList();
    private Numbers range = new Numbers("Range", 2.0, 1.0, 3.0, 1.0);
    private Numbers bps = new Numbers("BPS", 10.0, 1.0, 60.0, 1.0);
    private TimerUtil timer = new TimerUtil();
    private TimerUtil brokenResetTimer = new TimerUtil();

    public RouteHelper() {
        super("RouteHelper", new String[0], ModuleType.Macros);
        this.addValues(this.bps);
    }

    @EventHandler
    public void tick(EventRender2D eventRender2D) {
        if (this.brokenResetTimer.hasReached(1000.0)) {
            this.broken.clear();
            this.brokenResetTimer.reset();
        }
        if (!this.timer.hasReached(1000 / ((Double)this.bps.getValue()).intValue())) {
            return;
        }
        this.blockPos = this.getClosest();
        if (this.blockPos != null) {
            this.broken.add(this.blockPos);
            this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.blockPos, EnumFacing.DOWN));
            this.mc.field_71439_g.func_71038_i();
        }
        this.timer.reset();
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        for (int i = 0; i < this.routes.size(); ++i) {
            RenderUtil.drawSolidBlockESP((BlockPos)this.routes.get(i), Colors.WHITE.c, eventRender3D.getPartialTicks());
        }
    }

    private BlockPos getClosest() {
        Object object22;
        this.routeVecs = this.updateVecs();
        if (this.routeVecs.isEmpty()) {
            return null;
        }
        int n = ((Double)this.range.getValue()).intValue();
        BlockPos blockPos = this.mc.field_71439_g.func_180425_c().func_177982_a(0, 1, 0);
        Vec3i vec3i = new Vec3i(n, 3, n);
        Vec3i vec3i2 = new Vec3i(n, 3, n);
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        if (blockPos != null) {
            for (Object object22 : BlockPos.func_177980_a((BlockPos)blockPos.func_177971_a(vec3i), (BlockPos)blockPos.func_177973_b(vec3i2))) {
                IBlockState object3 = this.mc.field_71441_e.func_180495_p((BlockPos)object22);
                Block block = object3.func_177230_c();
                if (block != Blocks.field_150348_b && block != Blocks.field_150365_q && block != Blocks.field_150482_ag && block != Blocks.field_150352_o && block != Blocks.field_150450_ax && block != Blocks.field_150366_p && block != Blocks.field_150369_x && block != Blocks.field_150412_bA && block != Blocks.field_150424_aL && block != Blocks.field_150351_n && block != Blocks.field_150346_d || this.broken.contains(object22)) continue;
                arrayList.add(Vec3d.ofCenter((Vec3i)object22));
            }
        }
        arrayList.removeIf(this::checkDistance);
        arrayList.sort(Comparator.comparingDouble(this::lambda$getClosest$0));
        ArrayList arrayList2 = new ArrayList();
        for (Vec3d vec3d : arrayList) {
            arrayList2.add(new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
        }
        arrayList2.removeIf(this::checkPosUnder);
        this.routes.clear();
        this.routes.addAll(arrayList2.stream().distinct().collect(Collectors.toList()));
        if (arrayList.isEmpty()) {
            return null;
        }
        object22 = (Vec3d)arrayList.get(0);
        return new BlockPos(((Vec3d)object22).getX(), ((Vec3d)object22).getY(), ((Vec3d)object22).getZ());
    }

    private boolean checkDistance(Vec3d vec3d) {
        for (Vec3d vec3d2 : this.routeVecs) {
            if (!(vec3d2.distanceTo(vec3d) <= (Double)this.range.getValue())) continue;
            return false;
        }
        return true;
    }

    private boolean checkPosUnder(BlockPos blockPos) {
        AutoRuby autoRuby = (AutoRuby)Client.instance.getModuleManager().getModuleByClass(AutoRuby.class);
        for (int i = 0; i < autoRuby.wps.size(); ++i) {
            BlockPos blockPos2 = (BlockPos)autoRuby.wps.get(i);
            if (blockPos.func_177958_n() != blockPos2.func_177958_n() || blockPos.func_177952_p() != blockPos2.func_177952_p() || blockPos.func_177956_o() <= blockPos2.func_177956_o()) continue;
            return true;
        }
        return false;
    }

    private ArrayList updateVecs() {
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        AutoRuby autoRuby = (AutoRuby)Client.instance.getModuleManager().getModuleByClass(AutoRuby.class);
        for (int i = 0; i < autoRuby.wps.size(); ++i) {
            Vec3d vec3d = Vec3d.ofCenter((Vec3i)((BlockPos)autoRuby.wps.get(i)).func_177981_b(2));
            Vec3d vec3d2 = Vec3d.ofCenter(i + 1 == autoRuby.wps.size() ? (Vec3i)autoRuby.wps.get(0) : (Vec3i)autoRuby.wps.get(i + 1));
            double d = vec3d2.getX() - vec3d.getX();
            double d2 = vec3d2.getY() - vec3d.getY();
            double d3 = vec3d2.getZ() - vec3d.getZ();
            double d4 = this.getMax(Math.abs(d), Math.abs(d2), Math.abs(d3)) * 25.0;
            double d5 = d / d4;
            double d6 = d2 / d4;
            double d7 = d3 / d4;
            double d8 = vec3d.getX();
            double d9 = vec3d.getY();
            double d10 = vec3d.getZ();
            int n = 1;
            while ((double)n <= d4) {
                arrayList.add(new Vec3d(d8 += d5, d9 += d6, d10 += d7));
                ++n;
            }
        }
        return arrayList;
    }

    private double getMax(double d, double d2, double d3) {
        double d4 = 0.0;
        d4 = d > d2 ? d : d2;
        if (d3 > d4) {
            d4 = d3;
        }
        return d4;
    }

    private double lambda$getClosest$0(Vec3d vec3d) {
        return vec3d.distanceTo(Vec3d.of((Vec3i)this.mc.field_71439_g.func_180425_c()));
    }
}

