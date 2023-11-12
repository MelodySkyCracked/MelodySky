/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.Vec3
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL.Dungeons.Devices;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoSimonSays
extends Module {
    private final List simonSaysQueue = new ArrayList();
    public final BlockPos simonSaysStart = new BlockPos(110, 121, 91);
    public boolean clickedSimonSays;
    private long lastInteractTime;
    private TimerUtil timer = new TimerUtil();
    private Numbers delay = new Numbers("Delay", 350.0, 200.0, 1000.0, 10.0);

    public AutoSimonSays() {
        super("AutoSimonSays", new String[]{"ss"}, ModuleType.Dungeons);
        this.addValues(this.delay);
        this.setModInfo("Auto Do Simon Says Device.");
    }

    @Override
    public void onDisable() {
        this.simonSaysQueue.clear();
        this.clickedSimonSays = false;
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        if (!Client.inDungeons) {
            return;
        }
        if (this.simonSaysQueue.size() != 0 && System.currentTimeMillis() - this.lastInteractTime >= ((Double)this.delay.getValue()).longValue() && this.mc.field_71441_e.func_180495_p(new BlockPos(110, 121, 92)).func_177230_c() == Blocks.field_150430_aB) {
            for (BlockPos blockPos : new ArrayList(this.simonSaysQueue)) {
                MovingObjectPosition movingObjectPosition = this.calculateInterceptLook(blockPos, 5.5f);
                if (movingObjectPosition == null || !this.mc.field_71442_b.func_178890_a(this.mc.field_71439_g, this.mc.field_71441_e, this.mc.field_71439_g.field_71071_by.func_70448_g(), blockPos, movingObjectPosition.field_178784_b, movingObjectPosition.field_72307_f)) continue;
                this.mc.field_71439_g.func_71038_i();
                this.simonSaysQueue.remove(blockPos);
                this.lastInteractTime = System.currentTimeMillis();
                break;
            }
        }
    }

    @EventHandler
    public void onPacket(BlockChangeEvent blockChangeEvent) {
        if (!Client.inDungeons) {
            return;
        }
        if (!(blockChangeEvent.getPosition().func_177958_n() != 111 || blockChangeEvent.getNewBlock().func_177230_c() != Blocks.field_180398_cJ || this.simonSaysQueue.size() != 0 && ((BlockPos)this.simonSaysQueue.get(this.simonSaysQueue.size() - 1)).equals((Object)blockChangeEvent.getPosition()))) {
            this.simonSaysQueue.add(new BlockPos(110, blockChangeEvent.getPosition().func_177956_o(), blockChangeEvent.getPosition().func_177952_p()));
            this.clickedSimonSays = true;
        }
    }

    @EventHandler
    private void onReset(BlockChangeEvent blockChangeEvent) {
        if (!Client.inDungeons) {
            return;
        }
        if (blockChangeEvent.getPosition() == this.simonSaysStart && this.timer.hasReached(750.0)) {
            this.simonSaysQueue.clear();
            this.clickedSimonSays = false;
            Helper.sendMessage("[AutoSS] AutoSS Reset.");
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load load) {
        this.simonSaysQueue.clear();
        this.clickedSimonSays = false;
    }

    public MovingObjectPosition calculateInterceptLook(BlockPos blockPos, float f) {
        Vec3 vec3;
        AxisAlignedBB axisAlignedBB = this.getBlockAABB(blockPos);
        Vec3 vec32 = this.getPositionEyes();
        if (vec32.func_72436_e(vec3 = AutoSimonSays.getMiddleOfAABB(axisAlignedBB)) > (double)(f * f)) {
            return null;
        }
        return axisAlignedBB.func_72327_a(vec32, vec3);
    }

    public Vec3 getPositionEyes() {
        return new Vec3(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + (double)this.fastEyeHeight(), this.mc.field_71439_g.field_70161_v);
    }

    public AxisAlignedBB getBlockAABB(BlockPos blockPos) {
        Block block = this.mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
        block.func_180654_a((IBlockAccess)this.mc.field_71441_e, blockPos);
        return block.func_180646_a((World)this.mc.field_71441_e, blockPos);
    }

    public float fastEyeHeight() {
        return this.mc.field_71439_g.func_70093_af() ? 1.54f : 1.62f;
    }

    public static Vec3 getMiddleOfAABB(AxisAlignedBB axisAlignedBB) {
        return new Vec3((axisAlignedBB.field_72336_d + axisAlignedBB.field_72340_a) / 2.0, (axisAlignedBB.field_72337_e + axisAlignedBB.field_72338_b) / 2.0, (axisAlignedBB.field_72334_f + axisAlignedBB.field_72339_c) / 2.0);
    }
}

